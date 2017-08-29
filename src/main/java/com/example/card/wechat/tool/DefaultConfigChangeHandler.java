package com.example.card.wechat.tool;

/**
 * Created by racoon on 2017/4/17.
 */
import com.github.sd4324530.fastweixin.api.config.ConfigChangeNotice;
import com.github.sd4324530.fastweixin.handle.AbstractApiConfigChangeHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultConfigChangeHandler extends AbstractApiConfigChangeHandle {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultConfigChangeHandler.class);

    @Override
    public void configChange(ConfigChangeNotice notice) {
        LOG.debug("收到通知.....");
        LOG.debug(notice.toJsonString());
    }
}
