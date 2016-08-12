package com.zyl.library.enums;

public enum AppUpdaterError {
    /**
     * 无可用的网络
     */
    NETWORK_NOT_AVAILABLE,

    /**
     * xml文件中的下载连接无效
     */
    XML_URL_MALFORMED,

    /**
     * xml文件无效
     */
    XML_ERROR,
    /**
     * 版本号无效
     */
    VERSION_INVALED
}
