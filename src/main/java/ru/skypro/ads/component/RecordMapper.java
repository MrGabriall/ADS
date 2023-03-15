package ru.skypro.ads.component;

import org.springframework.stereotype.Component;
import ru.skypro.ads.dto.*;
import ru.skypro.ads.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class RecordMapper {

    public User toEntity(UserRecord userRecord) {
        User user = new User();
        user.setId(userRecord.getId());
        user.setFirstName(userRecord.getFirstName());
        user.setLastName(userRecord.getLastName());
        user.setPhone(userRecord.getPhone());
        user.setCity(userRecord.getCity());
        user.setRegDate(userRecord.getRegDate());
        if (userRecord.getAvatarPath() != null) {
            Avatar avatar = new Avatar();
            avatar.setFilePath(userRecord.getAvatarPath());
            user.setAvatar(avatar);
        }
        return user;
    }

    public UserRecord toRecord(User user) {
        UserRecord userRecord = new UserRecord();
        userRecord.setId(user.getId());
        userRecord.setFirstName(user.getFirstName());
        userRecord.setLastName(user.getLastName());
        userRecord.setPhone(user.getPhone());
        userRecord.setRegDate(user.getRegDate());
        userRecord.setAvatarPath(toRecord(user.getAvatar()).getAvatarUrl());

        if (user.getCity() != null) {
            userRecord.setCity(user.getCity());
        }
        return userRecord;
    }

    public Comment toEntity(CommentRecord commentRecord, Ads ads, User author) {
        Comment comment = new Comment();
        comment.setAds(ads);
        comment.setAuthor(author);
        comment.setText(commentRecord.getText());
        comment.setCreatedAt(LocalDateTime.now().toString());
        return comment;
    }

    public CommentRecord toRecord(Comment comment) {
        CommentRecord commentRecord = new CommentRecord();
        commentRecord.setPk(comment.getId());
        commentRecord.setAuthorId(comment.getAuthor().getId());
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

    public AdsRecord toRecord(Ads ads) {
        AdsRecord adsRecord = new AdsRecord();
        adsRecord.setPk(ads.getId());
        adsRecord.setTitle(ads.getTitle());
        adsRecord.setPrice(ads.getPrice());
        if (ads.getImage() != null) {
            adsRecord.setImage(toRecord(ads.getImage()).getImageUrl());
        }
        adsRecord.setAuthor(ads.getAuthor().getId());
        return adsRecord;
    }

    public FullAdsRecord toFullAdsRecord(Ads ads) {
        FullAdsRecord fullAdsRecord = new FullAdsRecord();
        fullAdsRecord.setPk(ads.getId());
        fullAdsRecord.setTitle(ads.getTitle());
        fullAdsRecord.setPrice(ads.getPrice());
        fullAdsRecord.setImage(toRecord(ads.getImage()).getImageUrl());
        fullAdsRecord.setDescription(ads.getDescription());
        fullAdsRecord.setAuthorFirstName(ads.getAuthor().getFirstName());
        fullAdsRecord.setAuthorLastName(ads.getAuthor().getLastName());
        fullAdsRecord.setPhone(ads.getAuthor().getPhone());
        return fullAdsRecord;
    }

    public ImageRecord toRecord(Image image) {
        ImageRecord imageRecord = new ImageRecord();
        if (image.getId() != null) {
            imageRecord.setId(image.getId());
            imageRecord.setImageUrl("/ads/" + imageRecord.getId() + "/image");
        }
        return imageRecord;
    }

    public AvatarRecord toRecord(Avatar avatar) {
        AvatarRecord avatarRecord = new AvatarRecord();
        if (avatar != null) {
            avatarRecord.setId(avatar.getId());
            avatarRecord.setAvatarUrl("/users/me/get_avatar/" + avatar.getId());
        }
        return avatarRecord;
    }
    public User toEntity(RegisterReq registerReq) {
        User user = new User();
        user.setUsername(registerReq.getUsername());
        user.setFirstName(registerReq.getFirstName());
        user.setLastName(registerReq.getLastName());
        user.setPhone(registerReq.getPhone());
        user.setPassword(registerReq.getPassword());
        user.setRole(registerReq.getRole());
        user.setRegDate(LocalDate.now().toString());
        user.setEnabled(true);
        return user;
    }
}
