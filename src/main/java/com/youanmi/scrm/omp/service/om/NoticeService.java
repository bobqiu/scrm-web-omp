package com.youanmi.scrm.omp.service.om;

import com.youanmi.scrm.omp.dto.om.NoticeDto;
import net.wildpig.base.common.entity.PageData;

/**
 * 添加类的一句话简单描述。
 * <p>
 * 详细描述
 *
 * @author xiao8 on 2016/12/3
 * @since ${version}
 */
public interface NoticeService {

    public PageData queryNoticeList(PageData pageData) throws  Exception;

    public NoticeDto getNoticeById(Long id);

    public NoticeDto addNotice(NoticeDto noticeDto);

    public void editNotice(NoticeDto noticeDto);

    public void cancelSend(Long id,Long updateId);

    public void delNotice(Long id,Long updateId);

    public void sendNoticeTask();
}
