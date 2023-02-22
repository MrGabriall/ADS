package ru.skypro.ads.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.component.RecordMapper;
import ru.skypro.ads.dto.*;
import ru.skypro.ads.entity.Ads;
import ru.skypro.ads.entity.Comment;
import ru.skypro.ads.entity.Image;
import ru.skypro.ads.repository.AdsRepository;
import ru.skypro.ads.repository.CommentRepository;
import ru.skypro.ads.repository.ImageRepository;
import ru.skypro.ads.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdsService {
    Logger logger = LoggerFactory.getLogger(AdsService.class);

    private AdsRepository adsRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private RecordMapper recordMapper;
    private ImageService imageService;
    private final ImageRepository imageRepository;

    private AdsService(AdsRepository adsRepository,
                       CommentRepository commentRepository,
                       UserRepository userRepository, RecordMapper recordMapper, ImageService imageService,
                       ImageRepository imageRepository) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.recordMapper = recordMapper;
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    public ResponseWrapperAds getAllAds() {
        logger.info("Completing method of getAllAds()");
        List<AdsRecord> list = new ArrayList<>();
        List<Ads> listAds = adsRepository.findAll();

        for (int i = 0; i < listAds.size(); i++) {
            list.add(i, recordMapper.toRecord(listAds.get(i)));
        }
        logger.info("getAllAds() is Complete");
        return new ResponseWrapperAds(list.size(), list);
    }

    public AdsRecord addAds(CreateAdsReq createAdsReq, MultipartFile multipartFile) {
        logger.info("Completing method of addAds()");
        Ads ads = recordMapper.toEntity(createAdsReq);
        //TODO как получить UserID
        //ads.setAuthorId();
        adsRepository.save(ads);
        ads.setImage(imageService.addImage(multipartFile));
        logger.info("getAllAds() is Complete");
        return recordMapper.toRecord(ads);

    }

    public ResponseWrapperComment getAllCommentsById(Integer id) {
        logger.info("Completing method of getCommentsById()");
        List<CommentRecord> list = new ArrayList<>();
        List<Comment> listComments = commentRepository.findAllById(id);

        for (int i = 0; i < listComments.size(); i++) {
            list.add(i, recordMapper.toRecord(listComments.get(i)));
        }
        logger.info("getCommentsById() is Complete");
        return new ResponseWrapperComment(list.size(), list);
    }

    public CommentRecord addComments(Integer adsId, CommentRecord commentRecord) {
        logger.info("Completing method of addComments()");
        Comment comment = recordMapper.toEntity(commentRecord);
        comment.setAdsId(adsRepository.findAdsById(adsId));
        commentRepository.save(comment);
        logger.info("addComments() is Complete");
        return commentRecord;
    }

    public FullAdsRecord getFullAds(Integer id) {
        logger.info("Completing method of getFullAds()");
        Ads ads = adsRepository.findAdsById(id);
        logger.info("getFullAds() is Complete");
        return recordMapper.toFullAdsRecord(ads);
    }

    public void deleteAds(Integer id) {
        logger.info("deleteAds() is Complete");
        adsRepository.deleteById(id);
    }

    public AdsRecord updateAds(Integer id, CreateAdsReq createAdsReq) {
        logger.info("Completing method of updateAds()");
        Ads ads = adsRepository.findAdsById(id);
        Ads newAds = recordMapper.toEntity(createAdsReq);

        ads.setDescription(newAds.getDescription());
        ads.setPrice(newAds.getPrice());
        ads.setTitle(newAds.getTitle());

        adsRepository.save(ads);
        logger.info("updateAds() is Complete");
        return recordMapper.toRecord(ads);
    }
    public CommentRecord getAdsComments(Integer adPk, Integer id) {
        logger.info("Completing method of getAdsComments()");
        Comment comment = commentRepository.findCommentByAdsIdAndId(adPk, id);
        logger.info("getAdsComments() is Complete");
        return recordMapper.toRecord(comment);
    }

    public boolean deleteComments(Integer adPk, Integer id) {
        logger.info("deleteComments() is Complete");
        return commentRepository.deleteByAdsIdAndId(adPk, id);
    }

    public CommentRecord updateComments(Integer adPk, Integer id, CommentRecord commentRecord) {
        logger.info("Completing method of updateComments()");

        Comment oldComment = commentRepository.findCommentByAdsIdAndId(adPk, id);
        Comment comment = recordMapper.toEntity(commentRecord);
        oldComment.setText(comment.getText());
        commentRepository.save(comment);
        logger.info("updateComments() is Complete");
        return commentRecord;
    }

    public Pair<byte[], String> updateAdsImage(Integer idAds, MultipartFile image){
        Ads ads = adsRepository.findAdsById(idAds);
        Image oldImage = imageRepository.findByAdsId(ads);
        Image newImage = imageService.updateImage(oldImage, image);
        return imageService.getImageData(newImage);
    }






}
