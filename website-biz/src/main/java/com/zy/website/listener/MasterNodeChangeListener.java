package com.zy.website.listener;

import com.github.ltsopensource.core.cluster.Node;
import com.github.ltsopensource.core.commons.utils.StringUtils;
import com.github.ltsopensource.core.listener.MasterChangeListener;
import com.github.ltsopensource.spring.boot.annotation.MasterNodeListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 主节点监听
 * todo 暂时未搞懂
 * https://gitcode.net/mirrors/ltsopensource/light-task-scheduler?utm_source=csdn_github_accelerator&from_codechina=yes
 */
@MasterNodeListener
public class MasterNodeChangeListener implements MasterChangeListener {

    private static Logger logger = LogManager.getLogger(MasterNodeChangeListener.class);

    /**
     * @param master   master节点
     * @param isMaster 表示当前节点是不是master节点
     */
    @Override
    public void change(Node master, boolean isMaster) {
        logger.info("LTS=====================>>>>>>>>>>监听");
        // 一个节点组master节点变化后的处理 , 譬如我多个JobClient， 但是有些事情只想只有一个节点能做。 定时任务可以只放在一台主服务上执行，分布式避免出现重复执行
        if (isMaster) {
            logger.info("我变成了节点组中的master节点了， 恭喜， 我要放大招了");
        } else {
            logger.info(StringUtils.format("master节点变成了{}，不是我，我不能放大招，要猥琐", master));
        }
    }

}