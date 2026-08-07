import service from '@/utils/request'

/**
 * 注册接口
 * @param {*} data  注册数据
 * @returns 注册结果
 */
export function register(data) {
    return service.post('/user/add', data)
}

/**
 * 创建新的会话
 * @param {*} data  会话数据
 * @returns 会话结果
 */
export function startSession(data) {
    return service.post('/psychological-chat/session/start', data)
}

/**
 * 分页查询咨询会话
 * @param {*} params  分页查询参数
 * @returns 会话列表
 */
export function getSessionList(params) {
    return service.get('/psychological-chat/sessions', { params })
}

/**
 * 获取会话消息列表
 * @param {*} sessionId 会话ID
 * @returns 会话消息列表
 */
export function getSessionMessageList(sessionId) {
    return service.get(`/psychological-chat/sessions/${sessionId}/messages`)
}

/**
 * 删除咨询会话
 * @param {*} sessionId 会话ID
 * @returns 删除结果
 */
export function deleteSession(sessionId) {
    return service.delete(`/psychological-chat/sessions/${sessionId}`)
}

/**
 * 获取会话情绪分析结果
 * @param {*} sessionId 会话ID
 * @returns 情绪分析结果
 */
export function getEmotionAnalysis(sessionId) {
    return service.get(`/psychological-chat/session/${sessionId}/emotion`)
}

/**
 * 创建或更新情绪日记
 * @param {*} data  情绪日记数据
 * @returns 创建或更新结果
 * */
export function submitDiaryData(data) {
    return service.post('/emotion-diary', data)
}

/**
 * 查询知识文章列表
 * @param {*} params  分页查询参数
 * @returns 文章列表
 */
export function getKnowledgeList(params) {
    return service.get('/knowledge/article/page', { params })
}

/**
 * 获取知识文章详情
 * @param {*} id 文章ID
 * @returns 文章详情
 */
export function getKnowledgeDetail(id) {
    return service.get(`/knowledge/article/${id}`)
}