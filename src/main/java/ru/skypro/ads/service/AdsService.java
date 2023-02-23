package ru.skypro.ads.service;

import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ru.skypro.ads.repository.AdsRepository;
import ru.skypro.ads.repository.CommentRepository;
import ru.skypro.ads.repository.ImageRepository;
import ru.skypro.ads.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdsService {

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

    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        List<AdsRecord> list = new ArrayList<>();
        List<Ads> listAds = adsRepository.findAll();

        for (int i = 0; i < listAds.size(); i++) {
            list.add(i, recordMapper.toRecord(listAds.get(i)));
        }
        return new ResponseEntity<>(new ResponseWrapperAds(list.size(), list), HttpStatus.OK);
    }

    public ResponseEntity<AdsRecord> addAds(CreateAdsReq createAdsReq, MultipartFile multipartFile) {
        Ads ads = recordMapper.toEntity(createAdsReq);
        //TODO как получить UserID
        //ads.setAuthorId();
        adsRepository.save(ads);
        ads.setImage(imageService.addImage(multipartFile));
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
        comment.setAdsId(adsRepository.findAdsById(adsId));
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
        Comment comment = commentRepository.findCommentByAdsIdAndId(adPk, id);
        return new ResponseEntity<>(recordMapper.toRecord(comment), HttpStatus.OK);
    }

    public boolean deleteComments(Integer adPk, Integer id) {
        return commentRepository.deleteByAdsIdAndId(adPk, id);
    }

    public ResponseEntity<CommentRecord> updateComments(Integer adPk, Integer id, CommentRecord commentRecord) {
        Comment oldComment = commentRepository.findCommentByAdsIdAndId(adPk, id);
        Comment comment = recordMapper.toEntity(commentRecord);
        oldComment.setText(comment.getText());
        commentRepository.save(comment);
        return new ResponseEntity<>(commentRecord, HttpStatus.OK);
    }

    public Pair<byte[], String> updateAdsImage(Integer idAds, MultipartFile image){
        Ads ads = adsRepository.findAdsById(idAds);
        Image oldImage = imageRepository.findByAdsId(ads);
        Image newImage = imageService.updateImage(oldImage, image);
        return imageService.getImageData(newImage);
    }






}
