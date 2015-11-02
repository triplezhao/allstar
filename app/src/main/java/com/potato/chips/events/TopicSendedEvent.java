package com.potato.chips.events;

import com.potato.sticker.main.data.bean.TopicBean;

/**
 * Created by ztw on 2015/11/2.
 */
public class TopicSendedEvent {
    int code = 0;
    TopicBean topicBean;

    public TopicSendedEvent(TopicBean topicBean) {
        this.topicBean = topicBean;
    }
}
