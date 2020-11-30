package com.cn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "yx_video")
public class Video implements Serializable {

        @Id
        private String id;

        private String title;

        private String brief;

        @Column(name = "cover_path")
        private String coverPath;

        @Column(name = "video_path")
        private String videoPath;    // 视频



        @Column(name = "upload_time")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date uploadTime;

        @Transient
        private String likeCount;

        @Transient
        private String playCount;

        @Column(name = "category_id")
        private String categoryId;

        @Column(name = "user_id")
        private String userId;

        @Column(name = "group_id")
        private String groupId;


}




