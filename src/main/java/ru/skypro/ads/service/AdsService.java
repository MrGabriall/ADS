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
import ru.skypro.ads.exception.ImageNotFoundException;
import ru.skypro.ads.repository.AdsRepository;
import ru.skypro.ads.repository.CommentRepository;
import ru.skypro.ads.repository.ImageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdsService {

    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final RecordMapper recordMapper;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final UserService userService;

    public AdsService(AdsRepository adsRepository,
                       CommentRepository commentRepository,
                       RecordMapper recordMapper, ImageService imageService,
                       ImageRepository imageRepository, UserService userService) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.recordMapper = recordMapper;
        this.imageService = imageService;
        this.imageRepository = imageRepository;
        this.userService = userService;
    }

    public ResponseWrapperAds getAllAds() {
        List<Ads> listAds = adsRepository.findAll();
        List<AdsRecord> listAdsRecords = new ArrayList<>();

        for (int i = 0; i < listAds.size(); i++) {
            listAdsRecords.add(recordMapper.toRecord(listAds.get(i)));
        }
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds(listAdsRecords.size(), listAdsRecords);
        System.out.println(responseWrapperAds);
        return responseWrapperAds;
    }

    public AdsRecord addAds(CreateAdsReq createAdsReq, MultipartFile multipartFile) {
        Ads ads = recordMapper.toEntity(createAdsReq);
        System.out.println(ads);
        UserRecord user = userService.getUser();
        ads.setAuthor(recordMapper.toEntity(user));
        ads = adsRepository.save(ads);
        ads.setImage(imageService.addImage(ads, multipartFile));
        ads = adsRepository.save(ads);
        return recordMapper.toRecord(ads);
    }

    public ResponseWrapperComment getAllCommentsById(Integer id) {
        List<CommentRecord> list = new ArrayList<>();
        List<Comment> listComments = commentRepository.findAllById(id);

        for (int i = 0; i < listComments.size(); i++) {
            list.add(i, recordMapper.toRecord(listComments.get(i)));
        }
        return new ResponseWrapperComment(list.size(), list);
    }

    public CommentRecord addComments(Integer adsId, CommentRecord commentRecord) {
        Comment comment = recordMapper.toEntity(commentRecord);
        comment.setAds(adsRepository.findById(adsId).orElseThrow(() -> new AdsNotFoundException()));
        commentRepository.save(comment);
        return commentRecord;
    }

    public FullAdsRecord getFullAds(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new AdsNotFoundException());
        return recordMapper.toFullAdsRecord(ads);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteAds(Integer id) {
        //TODO написать удаление комментов и картинок
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new AdsNotFoundException());
        Image image = ads.getImage();
        if (image != null) {
            if (imageService.deleteImage(image) && commentRepository.removeAllByAdsId(ads.getId())) {
                adsRepository.deleteById(id);
                //TODO Написать эксепшны и добавить в хендлер
            } else throw new RuntimeException("Exception with delete image or comments");
        } else throw new RuntimeException("Exception with find image");


    }

    public AdsRecord updateAds(Integer id, CreateAdsReq createAdsReq) {
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new AdsNotFoundException());
        Ads newAds = recordMapper.toEntity(createAdsReq);
        ads.setDescription(newAds.getDescription());
        ads.setPrice(newAds.getPrice());
        ads.setTitle(newAds.getTitle());
        adsRepository.save(ads);
        return recordMapper.toRecord(ads);
    }

    public CommentRecord getAdsComment(Integer adPk, Integer id) {
        Ads ads = adsRepository.findById(adPk).orElseThrow(() -> new AdsNotFoundException());
        Comment comment = commentRepository.findCommentByAdsAndId(ads, id);
        return recordMapper.toRecord(comment);
    }

    public boolean deleteComment(Integer adPk, Integer id) {
        Ads ads = adsRepository.findById(adPk).orElseThrow(() -> new AdsNotFoundException());
        return commentRepository.deleteByAdsAndId(ads, id);
    }

    public CommentRecord updateComment(Integer adPk, Integer id, CommentRecord commentRecord) {
        Ads ads = adsRepository.findById(adPk).orElseThrow(() -> new AdsNotFoundException());
        Comment oldComment = commentRepository.findCommentByAdsAndId(ads, id);
        oldComment.setText(commentRecord.getText());
        commentRepository.save(oldComment);
        return recordMapper.toRecord(oldComment);
    }

    public Pair<byte[], String> updateAdsImage(Integer idAds, MultipartFile image) {
        Ads ads = adsRepository.findAdsById(idAds);
        Image oldImage = imageRepository.findByAdsId(ads);
        Image newImage = imageService.updateImage(ads, oldImage, image);
        return imageService.getImageData(newImage);
    }

    public ResponseWrapperAds getAdsMe() {
        List<AdsRecord> list = new ArrayList<>();

        UserRecord userRecord = userService.getUser();
        User user = recordMapper.toEntity(userRecord);

        List<Ads> listAds = adsRepository.findAllByAuthorId(user.getId());
        for (int i = 0; i < listAds.size(); i++) {
            list.add(i, recordMapper.toRecord(listAds.get(i)));
        }
        return new ResponseWrapperAds(list.size(), list);
    }

    public Pair<byte[], String> getAdsById(Integer uniqId) {
        Image image = imageRepository.findById(uniqId).orElseThrow(() -> new ImageNotFoundException());
        return imageService.getImageData(image);
    }

}
