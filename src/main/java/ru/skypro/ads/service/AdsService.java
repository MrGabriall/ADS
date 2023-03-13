package ru.skypro.ads.service;

import org.springframework.data.util.Pair;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final UserRepository userRepository;

    public AdsService(AdsRepository adsRepository,
                      CommentRepository commentRepository,
                      RecordMapper recordMapper, ImageServiceImpl imageServiceImpl,
                      ImageRepository imageRepository, UserRepository userRepository) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.recordMapper = recordMapper;
        this.imageServiceImpl = imageServiceImpl;
        this.imageRepository = imageRepository;
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

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public AdsRecord addAds(CreateAdsReq createAdsReq, MultipartFile multipartFile, String username) {
        Ads ads = recordMapper.toEntity(createAdsReq);
        UserRecord user = recordMapper.toRecord(userRepository.findByUsername(username));
        ads.setAuthor(recordMapper.toEntity(user));
        ads = adsRepository.save(ads);
        ads.setImage(imageServiceImpl.addImage(ads, multipartFile));
        ads = adsRepository.save(ads);
        return recordMapper.toRecord(ads);
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseWrapperComment getAllCommentsByAdsId(Integer adsId) {
        List<CommentRecord> list = new ArrayList<>();
        Ads ads = adsRepository.findAdsById(adsId);
        List<Comment> listComments = commentRepository.findAllByAds(ads);

        for (int i = 0; i < listComments.size(); i++) {
            list.add(i, recordMapper.toRecord(listComments.get(i)));
        }
        return new ResponseWrapperComment(list.size(), list);
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public FullAdsRecord getFullAds(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        return recordMapper.toFullAdsRecord(ads);
    }

    @PreAuthorize("@adsService.isAdsAuthor(#adsId, #username) or hasAuthority('ROLE_ADMIN')")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteAds(Integer adsId, String username) {
        Ads ads = adsRepository.findById(adsId).orElseThrow(AdsNotFoundException::new);
        Image image = ads.getImage();
        if (image != null) {
            if (!imageServiceImpl.deleteImage(image)) {
                throw new ImageNotFoundException();
            }
        }
        commentRepository.removeAllByAds(ads);
        adsRepository.deleteById(adsId);
    }

    @PreAuthorize("@adsService.isAdsAuthor(#id, #username) or hasAuthority('ROLE_ADMIN')")
    public AdsRecord updateAds(Integer id, CreateAdsReq createAdsReq, String username) {
        Ads ads = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        Ads newAds = recordMapper.toEntity(createAdsReq);
        ads.setDescription(newAds.getDescription());
        ads.setPrice(newAds.getPrice());
        ads.setTitle(newAds.getTitle());
        adsRepository.save(ads);
        return recordMapper.toRecord(ads);
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public CommentRecord addComment(Integer adsId, CommentRecord commentRecord, String username) {
        Ads ads = adsRepository.findById(adsId).orElseThrow(AdsNotFoundException::new);
        User author = userRepository.findByUsername(username);
        Comment comment = recordMapper.toEntity(commentRecord, ads, author);
        comment = commentRepository.save(comment);
        return recordMapper.toRecord(comment);
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public CommentRecord getAdsComment(Integer adPk, Integer commentId) {
        Ads ads = adsRepository.findById(adPk).orElseThrow(AdsNotFoundException::new);
        Comment comment = commentRepository.findCommentByAdsAndId(ads, commentId)
                .orElseThrow(CommentNotFoundException::new);
        return recordMapper.toRecord(comment);
    }

    @PreAuthorize("@adsService.isCommentAuthor(#id, #username) or hasAuthority('ROLE_ADMIN')")
    public void deleteComment(Integer adPk, Integer id, String username) {
        adsRepository.findById(adPk).orElseThrow(AdsNotFoundException::new);
        if (!commentRepository.existsById(id)) {
            throw new CommentNotFoundException();
        }
        commentRepository.deleteById(id);
    }

    @PreAuthorize("@adsService.isCommentAuthor(#id, #username) or hasAuthority('ROLE_ADMIN')")
    public CommentRecord updateComment(Integer adPk, Integer id, CommentRecord commentRecord, String username) {
        Ads ads = adsRepository.findById(adPk).orElseThrow(AdsNotFoundException::new);
        Comment oldComment = commentRepository.findCommentByAdsAndId(ads, id)
                .orElseThrow(CommentNotFoundException::new);
        oldComment.setText(commentRecord.getText());
        commentRepository.save(oldComment);
        return recordMapper.toRecord(oldComment);
    }

    @PreAuthorize("@adsService.isAdsAuthor(#idAds, #username) or hasAuthority('ROLE_ADMIN')")
    public Pair<byte[], String> updateAdsImage(Integer idAds, MultipartFile image, String username) {
        Ads ads = adsRepository.findById(idAds).orElseThrow(AdsNotFoundException::new);
        Image oldImage = imageRepository.findByAdsId(ads.getId());
        Image newImage = imageServiceImpl.updateImage(ads, oldImage, image);
        ads.setImage(newImage);
        adsRepository.save(ads);
        return imageServiceImpl.getImageData(newImage);
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseWrapperAds getAdsMe(String username) {
        List<AdsRecord> list = new ArrayList<>();

        UserRecord userRecord = recordMapper.toRecord(userRepository.findByUsername(username));
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

    public boolean isAdsAuthor(Integer adsId, String username) {
        return adsRepository.existsByIdAndAuthor_Username(adsId, username);
    }

    public boolean isCommentAuthor(Integer commentId, String username) {
        return commentRepository.existsByIdAndAuthor_Username(commentId, username);
    }


}
