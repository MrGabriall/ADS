package ru.skypro.ads.component;

import org.springframework.stereotype.Component;
import ru.skypro.ads.dto.*;
import ru.skypro.ads.entity.*;

import java.time.LocalDateTime;

@Component
public class RecordMapper {

    public User toEntity(UserRecord userRecord) {
        User user = new User();
        user.setFirstName(userRecord.getFirstName());
        user.setLastName(userRecord.getLastName());
        user.setCity(userRecord.getCity());
        user.setEmail(userRecord.getEmail());
        //TODO уточнить необходимость этого сета
        user.setRegDate(userRecord.getRegDate());
        user.setPhone(userRecord.getPhone());
        if (userRecord.getImage() != null) {
            Avatar avatar = new Avatar();
            avatar.setFilePath(userRecord.getImage());
            user.setAvatar(avatar);
        }
        return user;
    }

    public User toEntity(RegisterReq registerReq) {
        User user = new User();
        user.setUserName(registerReq.getUsername());
        user.setPassword(registerReq.getPassword());
        user.setFirstName(registerReq.getFirstName());
        user.setLastName(registerReq.getLastName());
        user.setPhone(registerReq.getPhone());
        user.setRegDate(LocalDateTime.now().toString());
        return user;
    }

    public UserRecord toRecord(User user) {
        UserRecord userRecord = new UserRecord();
        userRecord.setFirstName(userRecord.getFirstName());
        userRecord.setLastName(userRecord.getLastName());
        userRecord.setPhone(userRecord.getPhone());
        userRecord.setRegDate(userRecord.getRegDate());
        userRecord.setId(userRecord.getId());

        if (user.getAvatar() != null) {
            userRecord.setImage(user.getAvatar().getFilePath());
        }
        if (user.getEmail() != null) {
            userRecord.setEmail(user.getEmail());
        }
        if (user.getCity() != null) {
            userRecord.setCity(user.getCity());
        }
        return userRecord;
    }

    public Comment toEntity(CommentRecord commentRecord) {
        Comment comment = new Comment();

        if (commentRecord.getAdsId() != null) {
            Ads ads = new Ads();
            ads.setId(commentRecord.getAdsId());
            comment.setAds(ads);
        }

        if (commentRecord.getAuthor() != null) {
            User user = new User();
            user.setId(commentRecord.getAuthor());
            comment.setAuthor(user);
        }

        comment.setText(comment.getText());
        comment.setCreatedAt(comment.getCreatedAt());
        return comment;
    }

    public CommentRecord toRecord(Comment comment) {
        CommentRecord commentRecord = new CommentRecord();
        commentRecord.setPk(comment.getId());
        commentRecord.setAuthor(comment.getAuthor().getId());
        commentRecord.setAdsId(comment.getAds().getId());
        commentRecord.setText(comment.getText());
        commentRecord.setCreatedAt(comment.getCreatedAt());
        return commentRecord;
    }

    public Ads toEntity(CreateAdsReq createAdsReq) {
        Ads ads = new Ads();
        ads.setTitle(createAdsReq.getTitle());
        ads.setPrice(createAdsReq.getPrice());
        ads.setDescription(createAdsReq.getDescription());
        return ads;
    }

    public Ads toEntity(AdsRecord adsRecord) {
        Ads ads = new Ads();

        if (adsRecord.getAuthor() != null) {
            User user = new User();
            user.setId(adsRecord.getAuthor());
            ads.setAuthor(user);
        }

        if (adsRecord.getImage() != null) {
            Image image = new Image();
            image.setFilePath(adsRecord.getImage());
            image.setAds(ads);
            ads.setImage(image);
        }

        ads.setPrice(adsRecord.getPrice());
        ads.setTitle(adsRecord.getTitle());
        return ads;
    }

    public AdsRecord toRecord(Ads ads) {
        AdsRecord adsRecord = new AdsRecord();
        adsRecord.setPk(ads.getId());
        adsRecord.setTitle(ads.getTitle());
        adsRecord.setPrice(ads.getPrice());
        adsRecord.setImage(ads.getImage().getFilePath());
        adsRecord.setAuthor(ads.getAuthor().getId());
        return adsRecord;
    }

    public FullAdsRecord toFullAdsRecord(Ads ads) {
        FullAdsRecord fullAdsRecord = new FullAdsRecord();
        fullAdsRecord.setPk(ads.getId());
        fullAdsRecord.setTitle(ads.getTitle());
        fullAdsRecord.setPrice(ads.getPrice());
        fullAdsRecord.setImage(ads.getImage().getFilePath());
        fullAdsRecord.setDescription(ads.getDescription());
        fullAdsRecord.setAuthorFirstName(ads.getAuthor().getFirstName());
        fullAdsRecord.setAuthorLastName(ads.getAuthor().getLastName());
        fullAdsRecord.setPhone(ads.getAuthor().getPhone());
        fullAdsRecord.setEmail(ads.getAuthor().getEmail());
        return fullAdsRecord;
    }
}
