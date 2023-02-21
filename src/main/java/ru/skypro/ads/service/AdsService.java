package ru.skypro.ads.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.dto.*;
import ru.skypro.ads.entity.Ads;
import ru.skypro.ads.entity.Comment;
import ru.skypro.ads.entity.Image;
import ru.skypro.ads.entity.User;
import ru.skypro.ads.repository.AdsRepository;
import ru.skypro.ads.repository.CommentRepository;
import ru.skypro.ads.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdsService {
    Logger logger = LoggerFactory.getLogger(AdsService.class);

    private AdsRepository adsRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;

    private AdsService(AdsRepository adsRepository,
                       CommentRepository commentRepository,
                       UserRepository userRepository) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    private AdsRecord adsToAdsRecord(Ads ads) {
        return new AdsRecord(ads.getId(),
                ads.getTitle(),
                ads.getPrice(),
                ads.getImage().getFile(),
                ads.getAuthorId().getId());
    }

    private CommentRecord commentToCommentRecord(Comment comment) {
        return new CommentRecord(comment.getId(),
                comment.getAuthorId().getId(),
                comment.getAdsId().getId(),
                comment.getText(),
                comment.getCreatedAt());
    }

    private Comment commentRecordToComment(CommentRecord commentRecord) {
        User user  = userRepository.findById(commentRecord.getAuthor()).get();
        Ads ads = adsRepository.findById(commentRecord.getAdsId()).get();
        return new Comment(commentRecord.getPk(),
                user,
                ads,
                commentRecord.getText(),
                commentRecord.getCreatedAt());
    }

    private FullAdsRecord adsToFullAdsRecord(Ads ads) {
        return new FullAdsRecord(ads.getAuthorId().getFirstName(),
                ads.getAuthorId().getLastName(),
                ads.getAuthorId().getEmail(),
                ads.getAuthorId().getPhone(),
                ads.getDescription(),
                ads.getImage().getFile(),
                ads.getId(),
                ads.getPrice(),
                ads.getTitle());
    }

    private Ads createAdsReqToAds(Integer id, CreateAdsReq createAdsReq) {
        Ads ads = adsRepository.findById(id).get();
        return new Ads(id,
                ads.getImage(),
                createAdsReq.getTitle(),
                ads.getAuthorId(),
                createAdsReq.getDescription(),
                createAdsReq.getPrice());
    }

    public ResponseWrapperAds getAllAds() {
        logger.info("Completing method of getAllAds()");
        List<AdsRecord> list = new ArrayList<>();
        List<Ads> listAds = adsRepository.findAll();

        for (int i = 0; i < listAds.size(); i++) {
            list.add(i, adsToAdsRecord(listAds.get(i)));
        }
        logger.info("getAllAds() is Complete");
        return new ResponseWrapperAds(list.size(), list);
    }

    public AdsRecord addAds(CreateAdsReq createAdsReq, MultipartFile multipartFile) {
        logger.info("Completing method of addAds()");
        Ads ads = new Ads();
        ads = createAdsReqToAds(ads.getId(), createAdsReq);
        //TODO как получить UserID
        //ads.setAuthorId();
        adsRepository.save(ads);
        //TODO paste method of setting image in ads from Gabriell's pullRequest
        logger.info("getAllAds() is Complete");
        return adsToAdsRecord(ads);

    }

    public ResponseWrapperComment getAllCommentsById(Integer id) {
        logger.info("Completing method of getCommentsById()");
        List<CommentRecord> list = new ArrayList<>();
        List<Comment> listComments = commentRepository.findAllById();

        for (int i = 0; i < listComments.size(); i++) {
            list.add(i, commentToCommentRecord(listComments.get(i)));
        }
        logger.info("getCommentsById() is Complete");
        return new ResponseWrapperComment(list.size(), list);
    }

    public CommentRecord addComments(Integer Id, CommentRecord commentRecord) {
        logger.info("Completing method of addComments()");
        Comment comment = commentRecordToComment(commentRecord);
        commentRepository.save(comment);
        logger.info("addComments() is Complete");
        return commentRecord;
    }

    public FullAdsRecord getFullAds(Integer id) {
        logger.info("Completing method of getFullAds()");
        Ads ads = adsRepository.findById(id).orElseThrow();
        logger.info("getFullAds() is Complete");
        return adsToFullAdsRecord(ads);
    }

    public void deleteAds(Integer id) {
        logger.info("deleteAds() is Complete");
        adsRepository.deleteById(id);
    }

    public AdsRecord updateAds(Integer id, CreateAdsReq createAdsReq) {
        logger.info("Completing method of updateAds()");
        Ads ads = createAdsReqToAds(id, createAdsReq);
        adsRepository.save(ads);
        logger.info("updateAds() is Complete");
        return adsToAdsRecord(ads);
    }
//TODO Зачем здесь ADPK? Не понимаю логики в этом методе, если есть другой, так еще и со списком комментов
    public CommentRecord getAdsComments(Integer adPk, Integer id) {
        logger.info("Completing method of getAdsComments()");
        Comment comment = commentRepository.findById(id).get();
        logger.info("getAdsComments() is Complete");
        return commentToCommentRecord(comment);
    }

    public void deleteComments(Integer adPk, Integer id) {
        logger.info("deleteComments() is Complete");
        commentRepository.deleteById(id);
    }

    public CommentRecord updateComments(Integer adPk, Integer id, CommentRecord commentRecord) {
        logger.info("Completing method of updateComments()");
        Comment comment = commentRecordToComment(commentRecord);
        commentRepository.save(comment);
        logger.info("updateComments() is Complete");
        return commentRecord;
    }








}
