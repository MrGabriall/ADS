package ru.skypro.ads.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.component.RecordMapper;
import ru.skypro.ads.dto.*;
import ru.skypro.ads.entity.Ads;
import ru.skypro.ads.entity.Comment;
import ru.skypro.ads.entity.Image;
import ru.skypro.ads.entity.User;
import ru.skypro.ads.exception.AdsNotFoundException;
import ru.skypro.ads.exception.CommentNotFoundException;
import ru.skypro.ads.exception.ImageNotFoundException;
import ru.skypro.ads.repository.AdsRepository;
import ru.skypro.ads.repository.CommentRepository;
import ru.skypro.ads.repository.ImageRepository;
import ru.skypro.ads.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdsService {

    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final RecordMapper recordMapper;
    private final ImageServiceImpl imageServiceImpl;
    private final ImageRepository imageRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public AdsService(AdsRepository adsRepository,
                      CommentRepository commentRepository,
                      RecordMapper recordMapper, ImageServiceImpl imageServiceImpl,
                      ImageRepository imageRepository, UserService userService,
                      UserRepository userRepository) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.recordMapper = recordMapper;
        this.imageServiceImpl = imageServiceImpl;
        this.imageRepository = imageRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public ResponseWrapperAds getAllAds() {
        List<Ads> listAds = adsRepository.findAll();
        List<AdsRecord> listAdsRecords = new ArrayList<>();

        for (Ads listAd : listAds) {
            listAdsRecords.add(recordMapper.toRecord(listAd));
        }
        return new ResponseWrapperAds(listAdsRecords.size(), listAdsRecords);
    }
//todo добавлен parameter email
    public AdsRecord addAds(CreateAdsReq createAdsReq, MultipartFile multipartFile, String email) {
        Ads ads = recordMapper.toEntity(createAdsReq);
        UserRecord user = recordMapper.toRecord(userRepository.findByEmail(email));
        ads.setAuthor(recordMapper.toEntity(user));
        ads = adsRepository.save(ads);
        ads.setImage(imageServiceImpl.addImage(ads, multipartFile));
        ads = adsRepository.save(ads);
        return recordMapper.toRecord(ads);
    }

    public ResponseWrapperComment getAllCommentsByAdsId(Integer adsId) {
        List<CommentRecord> list = new ArrayList<>();
        Ads ads = adsRepository.findAdsById(adsId);
        List<Comment> listComments = commentRepository.findAllByAds(ads);

        for (int i = 0; i < listComments.size(); i++) {
            list.add(i, recordMapper.toRecord(listComments.get(i)));
        }
        return new ResponseWrapperComment(list.size(), list);
    }

    public FullAdsRecord getFullAds(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        return recordMapper.toFullAdsRecord(ads);
    }
//todo написана проверка на роль Админа или совпадение действующего юзера с автором объявления
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteAds(Integer id, String email) {
        User user = userRepository.findByEmail(email);
        Ads ads = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        if (!(user.getRole().equals("USER") && ads.getAuthor().getId() == user.getId()) || !user.getRole().equals("ADMIN")) {
            return;
        }
        Image image = ads.getImage();
        if (image != null) {
            if (!imageServiceImpl.deleteImage(image)) {
                throw new ImageNotFoundException();
            }
        }
        commentRepository.removeAllByAds(ads);
        adsRepository.deleteById(id);
    }
//todo checkRole done
    public AdsRecord updateAds(Integer id, CreateAdsReq createAdsReq, String email) {
        User user = userRepository.findByEmail(email);
        Ads ads = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        if (!(user.getRole().equals("USER") && ads.getAuthor().getId() == user.getId())) {
            throw new RuntimeException("К действию updateAds() доступ закрыт.");
        }
        Ads newAds = recordMapper.toEntity(createAdsReq);
        ads.setDescription(newAds.getDescription());
        ads.setPrice(newAds.getPrice());
        ads.setTitle(newAds.getTitle());
        adsRepository.save(ads);
        return recordMapper.toRecord(ads);
    }

    public CommentRecord addComment(Integer adsId, CommentRecord commentRecord) {

        Ads ads = adsRepository.findById(adsId).orElseThrow(AdsNotFoundException::new);
        User author = userRepository.findById(commentRecord.getAuthorId()).orElseThrow(RuntimeException::new);
        Comment comment = recordMapper.toEntity(commentRecord, ads, author);
        comment = commentRepository.save(comment);
        return recordMapper.toRecord(comment);
    }

    public CommentRecord getAdsComment(Integer adPk, Integer commentId) {
        Ads ads = adsRepository.findById(adPk).orElseThrow(AdsNotFoundException::new);
        Comment comment = commentRepository.findCommentByAdsAndId(ads, commentId)
                .orElseThrow(CommentNotFoundException::new);
        return recordMapper.toRecord(comment);
    }

    public void deleteComment(Integer adPk, Integer id) {
        Ads ads = adsRepository.findById(adPk).orElseThrow(AdsNotFoundException::new);
        if (!commentRepository.deleteByAdsAndId(ads, id)) {
            throw new CommentNotFoundException();
        }
    }

    public CommentRecord updateComment(Integer adPk, Integer id, CommentRecord commentRecord) {
        Ads ads = adsRepository.findById(adPk).orElseThrow(AdsNotFoundException::new);
        Comment oldComment = commentRepository.findCommentByAdsAndId(ads, id)
                .orElseThrow(CommentNotFoundException::new);
        oldComment.setText(commentRecord.getText());
        commentRepository.save(oldComment);
        return recordMapper.toRecord(oldComment);
    }

    public Pair<byte[], String> updateAdsImage(Integer idAds, MultipartFile image) {
        Ads ads = adsRepository.findById(idAds).orElseThrow(AdsNotFoundException::new);
        Image oldImage = imageRepository.findByAdsId(ads);
        Image newImage = imageServiceImpl.updateImage(ads, oldImage, image);
        return imageServiceImpl.getImageData(newImage);
    }
//todo добавлен параметер email
    public ResponseWrapperAds getAdsMe(String email) {
        List<AdsRecord> list = new ArrayList<>();

        UserRecord userRecord = recordMapper.toRecord(userRepository.findByEmail(email));
        User user = recordMapper.toEntity(userRecord);

        List<Ads> listAds = adsRepository.findAllByAuthorId(user.getId());
        for (int i = 0; i < listAds.size(); i++) {
            list.add(i, recordMapper.toRecord(listAds.get(i)));
        }
        return new ResponseWrapperAds(list.size(), list);
    }

    public Pair<byte[], String> getImageById(Integer uniqId) {
        Image image = imageRepository.findById(uniqId).orElseThrow(ImageNotFoundException::new);
        return imageServiceImpl.getImageData(image);
    }

}
