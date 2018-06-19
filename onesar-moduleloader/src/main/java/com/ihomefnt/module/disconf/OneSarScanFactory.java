package com.ihomefnt.module.disconf;

import com.baidu.disconf.client.scan.ScanMgr;
import com.baidu.disconf.client.support.registry.Registry;

public class OneSarScanFactory {

  /**
   * @throws Exception
   */
  public static ScanMgr getScanMgr(Registry registry) throws Exception {

    ScanMgr scanMgr = new OneSarScanMgrImpl(registry);
    return scanMgr;
  }
}
