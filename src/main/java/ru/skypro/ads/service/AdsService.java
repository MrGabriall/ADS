package ru.skypro.ads.service;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.component.RecordMapper;
import ru.skypro.ads.dto.*;
import ru.skypro.ads.entity.Ads;
import ru.skypro.ads.entity.Comment;
import ru.skypro.ads.entity.Image;
import ru.skypro.ads.entity.User;
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
    private final UserRepository userRepository;
    private final RecordMapper recordMapper;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final UserService userService;

    private AdsService(AdsRepository adsRepository,
                       CommentRepository commentRepository,
                       UserRepository userRepository, RecordMapper recordMapper, ImageService imageService,
                       ImageRepository imageRepository, UserService userService) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
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

    public ResponseEntity<AdsRecord> addAds(CreateAdsReq createAdsReq, MultipartFile multipartFile) {
        Ads ads = recordMapper.toEntity(createAdsReq);
        System.out.println(ads);
        UserRecord user = userService.getUser();
        ads.setAuthor(recordMapper.toEntity(user));
        //TODO придумать что-нибудь тут
        ads = adsRepository.save(ads);
        ads.setImage(imageService.addImage(ads, multipartFile));
        ads = adsRepository.save(ads);
        return new ResponseEntity<>(recordMapper.toRecord(ads), HttpStatus.OK);

    }

    public ResponseEntity<ResponseWrapperComment> getAllCommentsById(Integer id) {
        List<CommentRecord> list = new ArrayList<>();
        List<Comment> listComments = commentRepository.findAllById(id);

        for (int i = 0; i < listComments.size(); i++) {
            list.add(i, recordMapper.toRecord(listComments.get(i)));
        }
        return new ResponseEntity<>(new ResponseWrapperComment(list.size(), list), HttpStatus.OK);
    }

    public ResponseEntity<CommentRecord> addComments(Integer adsId, CommentRecord commentRecord) {
        Comment comment = recordMapper.toEntity(commentRecord);
        comment.setAds(adsRepository.findAdsById(adsId));
        commentRepository.save(comment);
        return new ResponseEntity<>(commentRecord, HttpStatus.OK);
    }

    public ResponseEntity<FullAdsRecord> getFullAds(Integer id) {
        Ads ads = adsRepository.findAdsById(id);
        return new ResponseEntity<>(recordMapper.toFullAdsRecord(ads), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteAds(Integer id) {
        adsRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<AdsRecord> updateAds(Integer id, CreateAdsReq createAdsReq) {
        Ads ads = adsRepository.findAdsById(id);
        Ads newAds = recordMapper.toEntity(createAdsReq);
        ads.setDescription(newAds.getDescription());
        ads.setPrice(newAds.getPrice());
        ads.setTitle(newAds.getTitle());
        adsRepository.save(ads);
        return new ResponseEntity<>(recordMapper.toRecord(ads), HttpStatus.OK);
    }

    public ResponseEntity<CommentRecord> getAdsComments(Integer adPk, Integer id) {
        Ads ads = adsRepository.findAdsById(adPk);
        Comment comment = commentRepository.findCommentByAdsAndId(ads, id);
        return new ResponseEntity<>(recordMapper.toRecord(comment), HttpStatus.OK);
    }

    public boolean deleteComments(Integer adPk, Integer id) {
        Ads ads = adsRepository.findAdsById(adPk);
        return commentRepository.deleteByAdsAndId(ads, id);
    }

    public ResponseEntity<CommentRecord> updateComments(Integer adPk, Integer id, CommentRecord commentRecord) {
        Ads ads = adsRepository.findAdsById(adPk);
        Comment oldComment = commentRepository.findCommentByAdsAndId(ads, id);
        Comment comment = recordMapper.toEntity(commentRecord);
        oldComment.setText(comment.getText());
        commentRepository.save(comment);
        return new ResponseEntity<>(commentRecord, HttpStatus.OK);
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

        List<Ads> listAds = adsRepository.findAllByAuthor(user);
        for (int i = 0; i < listAds.size(); i++) {
            list.add(i, recordMapper.toRecord(listAds.get(i)));
        }
        return new ResponseWrapperAds(list.size(), list);
    }

    public Pair<byte[], String> getAdsByUniqueId(Integer uniqId){
        Image image = imageRepository.getReferenceById(uniqId);
        return imageService.getImageData(image);
    }

}
