package com.tp.service.impl;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.tp.entity.KnowledgeCategory;
import com.tp.mapper.KnowledgeCategoryMapper;
import com.tp.service.KnowledgeCategoryService;
import org.springframework.stereotype.Service;

/**
 * 包名称：com.tp.service.impl
 * 类名称：KnowledgeCategoryServiceImpl
 * 类描述：知识文章分类服务实现类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */
@Service
public class KnowledgeCategoryServiceImpl extends ServiceImpl<KnowledgeCategoryMapper, KnowledgeCategory>
        implements KnowledgeCategoryService {
}
