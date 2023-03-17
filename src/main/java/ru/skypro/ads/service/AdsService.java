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

/**
 * Class for work with ads
 */
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

    /**
     * Method for return all ads
     *
     * @return response wrapper ads
     */
    public ResponseWrapperAds getAllAds() {
        List<Ads> listAds = adsRepository.findAll();
        List<AdsRecord> listAdsRecords = new ArrayList<>();

        for (Ads listAd : listAds) {
            listAdsRecords.add(recordMapper.toRecord(listAd));
        }
        return new ResponseWrapperAds(listAdsRecords.size(), listAdsRecords);
    }

    /**
     * Method for create ads entity
     *
     * @param createAdsReq
     * @param multipartFile
     * @param username
     * @return ads DTO
     */
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

    /**
     * Method for return all comments by ads id
     *
     * @param adsId
     * @return response wrapper comment
     */
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

    /**
     * Method for return full ads dto by ads id
     *
     * @param id
     * @return full ads DTO
     */
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public FullAdsRecord getFullAds(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        return recordMapper.toFullAdsRecord(ads);
    }

    /**
     * Method for delete ads, delete all comments by ads and image
     *
     * @param adsId
     * @param username
     */
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

    /**
     * Method for update ads
     *
     * @param id
     * @param createAdsReq
     * @param username
     * @return ads DTO
     */
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

    /**
     * Method for add comment by ads
     *
     * @param adsId
     * @param commentRecord
     * @param username
     * @return comment DTO
     */
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public CommentRecord addComment(Integer adsId, CommentRecord commentRecord, String username) {
        Ads ads = adsRepository.findById(adsId).orElseThrow(AdsNotFoundException::new);
        User author = userRepository.findByUsername(username);
        Comment comment = recordMapper.toEntity(commentRecord, ads, author);
        comment = commentRepository.save(comment);
        return recordMapper.toRecord(comment);
    }

    /**
     * Method for get comment by ads and comment id
     *
     * @param adPk
     * @param commentId
     * @return comment DTO
     */
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public CommentRecord getAdsComment(Integer adPk, Integer commentId) {
        Ads ads = adsRepository.findById(adPk).orElseThrow(AdsNotFoundException::new);
        Comment comment = commentRepository.findCommentByAdsAndId(ads, commentId)
                .orElseThrow(CommentNotFoundException::new);
        return recordMapper.toRecord(comment);
    }

    /**
     * Method for delete comment by ads
     *
     * @param adPk
     * @param id
     * @param username
     */
    @PreAuthorize("@adsService.isCommentAuthor(#id, #username) or hasAuthority('ROLE_ADMIN')")
    public void deleteComment(Integer adPk, Integer id, String username) {
        adsRepository.findById(adPk).orElseThrow(AdsNotFoundException::new);
        if (!commentRepository.existsById(id)) {
            throw new CommentNotFoundException();
        }
        commentRepository.deleteById(id);
    }

    /**
     * Method for update comment
     *
     * @param adPk
     * @param id
     * @param commentRecord
     * @param username
     * @return
     */
    @PreAuthorize("@adsService.isCommentAuthor(#id, #username) or hasAuthority('ROLE_ADMIN')")
    public CommentRecord updateComment(Integer adPk, Integer id, CommentRecord commentRecord, String username) {
        Ads ads = adsRepository.findById(adPk).orElseThrow(AdsNotFoundException::new);
        Comment oldComment = commentRepository.findCommentByAdsAndId(ads, id)
                .orElseThrow(CommentNotFoundException::new);
        oldComment.setText(commentRecord.getText());
        commentRepository.save(oldComment);
        return recordMapper.toRecord(oldComment);
    }

    /**
     * Method for update ads image in file system and db
     *
     * @param idAds
     * @param image
     * @param username
     * @return pair byte[] and media type
     */
    @PreAuthorize("@adsService.isAdsAuthor(#idAds, #username) or hasAuthority('ROLE_ADMIN')")
    public Pair<byte[], String> updateAdsImage(Integer idAds, MultipartFile image, String username) {
        Ads ads = adsRepository.findById(idAds).orElseThrow(AdsNotFoundException::new);
        Image oldImage = imageRepository.findByAdsId(ads.getId());
        Image newImage = imageServiceImpl.updateImage(ads, oldImage, image);
        ads.setImage(newImage);
        adsRepository.save(ads);
        return imageServiceImpl.getImageData(newImage);
    }

    /**
     * Method for get all ads by username
     *
     * @param username
     * @return response wrapper ads
     */
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

    /**
     * Method for get image by unique id
     * Unique id is image id
     *
     * @param uniqId
     * @return pair byte[] and media type
     */
    public Pair<byte[], String> getImageById(Integer uniqId) {
        Image image = imageRepository.findById(uniqId).orElseThrow(ImageNotFoundException::new);
        return imageServiceImpl.getImageData(image);
    }

    /**
     * Method for check author of ads by username
     *
     * @param adsId
     * @param username
     * @return boolean
     */
    public boolean isAdsAuthor(Integer adsId, String username) {
        return adsRepository.existsByIdAndAuthor_Username(adsId, username);
    }

    /**
     * Method for check author of comment by username
     *
     * @param commentId
     * @param username
     * @return boolean
     */
    public boolean isCommentAuthor(Integer commentId, String username) {
        return commentRepository.existsByIdAndAuthor_Username(commentId, username);
    }


}
