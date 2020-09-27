package com.peter.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author lcc
 * @date 2020/9/27 下午3:43
 */
@Slf4j
public class FileMonitorUtil extends FileAlterationListenerAdaptor {
    public FileMonitorUtil() {
        log.info("monitor");
    }

    @Override
    public void onStart(FileAlterationObserver observer) {
        log.info("start...");
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        log.info("stop...");
    }

    @Override
    public void onFileCreate(File file) {
        log.info(file.getName()+" create");
    }

    @Override
    public void onFileChange(File file) {
        log.info(file.getName()+" change");
    }

    @Override
    public void onFileDelete(File file) {
        log.info(file.getName()+" delete");
    }

    public static void main(String[] args) throws Exception {
        // 监控目录
        String rootDir = "/tmp/test";
        // 轮询间隔 3 秒
        long interval = TimeUnit.SECONDS.toMillis(3);
        // 创建过滤器
//        IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(),
                FileFilterUtils.suffixFileFilter(".txt"));
        IOFileFilter filter = FileFilterUtils.or(files);
        // 使用过滤器
        FileAlterationObserver observer = new FileAlterationObserver(new File(rootDir), filter);
        // 不使用过滤器
        // FileAlterationObserver observer = new FileAlterationObserver(new
        // File(rootDir));
        observer.addListener(new FileMonitorUtil());
        // 创建文件变化监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        // 开始监控
        monitor.start();
    }
}
