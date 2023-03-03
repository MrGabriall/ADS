package ru.skypro.ads.component;

import org.springframework.stereotype.Component;
import ru.skypro.ads.dto.*;
import ru.skypro.ads.entity.*;

@Component
public class RecordMapper {

    public User toEntity(UserRecord userRecord) {
        User user = new User();
        user.setId(userRecord.getId());
        user.setFirstName(userRecord.getFirstName());
        user.setLastName(userRecord.getLastName());
        user.setEmail(userRecord.getEmail());
        user.setPhone(userRecord.getPhone());
        user.setCity(userRecord.getCity());
        user.setRegDate(userRecord.getRegDate());
        if (userRecord.getAvatarPath() != null) {
            Avatar avatar = new Avatar();
            avatar.setFilePath("src/main/resources/files/avatars/basic_avatar.jpg");
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

        if (user.getEmail() != null) {
            userRecord.setEmail(user.getEmail());
        }
        if (user.getCity() != null) {
            userRecord.setCity(user.getCity());
        }
        return userRecord;
    }

    public Comment toEntity(CommentRecord commentRecord, Ads ads, User author) {
        Comment comment = new Comment();
        comment.setId(commentRecord.getPk());
        comment.setAds(ads);
        comment.setAuthor(author);
        comment.setText(commentRecord.getText());
        comment.setCreatedAt(commentRecord.getCreatedAt());
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
        //TODO
        fullAdsRecord.setImage(toRecord(ads.getImage()).getImageUrl());
        fullAdsRecord.setDescription(ads.getDescription());
        fullAdsRecord.setAuthorFirstName(ads.getAuthor().getFirstName());
        fullAdsRecord.setAuthorLastName(ads.getAuthor().getLastName());
        fullAdsRecord.setPhone(ads.getAuthor().getPhone());
        fullAdsRecord.setEmail(ads.getAuthor().getEmail());
        return fullAdsRecord;
    }

    public ImageRecord toRecord(Image image) {
        ImageRecord imageRecord = new ImageRecord();
        //TODO
        if (image.getId() != null) {
            imageRecord.setId(image.getId());
            imageRecord.setImageUrl("/ads/" + imageRecord.getId() + "/image");
        }
        return imageRecord;
    }

    public AvatarRecord toRecord(Avatar avatar) {
        AvatarRecord avatarRecord = new AvatarRecord();
        avatarRecord.setId(avatar.getId());
        avatarRecord.setAvatarUrl("/users/me/" + avatarRecord.getId());
        return avatarRecord;
    }
}
