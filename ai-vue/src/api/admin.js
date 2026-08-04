import service from '@/utils/request'

/**
 * 登录
 * @param {*} data  登录数据
 * @returns 登录结果
 * */
export function login(data) {
    return service.post('/user/login', data)
}

/**
 * 获取知识文章分类树
 * @returns 分类树
 * */
export function getCategoryTree() {
    return service.get('/knowledge/category/tree')
}

/**
 * 获取知识文章分页列表
 * @param {*} params  分页参数
 * @returns 文章分页列表
 * */
export function getKnowledgeArticlePage(params) {
    return service.get('/knowledge/article/page', { params: params })
}

/**
 * 上传文件
 * @param {*} file  文件
 * @param {*} businessInfo  业务信息
 * @returns 上传结果
 * */
export function uploadFile(file, businessInfo) {
    const formData = new FormData()
    formData.append('file', file)
    formData.append('businessType', 'ARTICLE')
    formData.append('businessId', businessInfo.businessId)
    formData.append('businessField', 'cover')
    return service.post('/file/upload', formData,
        {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }
    )
}

/**
 * 创建知识文章
 * @param {*} data  文章数据
 * @returns 创建结果
 * */
export function createKnowledgeArticle(data) {
    return service.post('/knowledge/article', data)
}

/**
 * 获取知识文章详情
 * @param {*} id  文章id
 * @returns 文章详情
 * */
export function getArticleDetail(id) {
    return service.get(`/knowledge/article/${id}`)
}

/**
 * 更新知识文章
 * @param {*} id  文章id
 * @param {*} data  文章数据
 * @returns 更新结果
 * */
export function updateKnowledgeArticle(id, data) {
    return service.put(`/knowledge/article/${id}`, data)
}

/**
 * 更新文章状态
 * @param {*} id  文章id
 * @param {*} data  状态数据
 * @returns 更新结果
 * */
export function updateKnowledgeArticleStatus(id, data) {
    return service.put(`/knowledge/article/${id}/status`, data)
}

/**
 * 删除知识文章
 * @param {*} id  文章id
 * @returns 删除结果
 * */
export function deleteKnowledgeArticle(id) {
    return service.delete(`/knowledge/article/${id}`)
}