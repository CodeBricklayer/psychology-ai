<template>
    <div>
        <PageHead title="情绪日志" />
        <TableSearch :formItem="formItem" @search="handleSearch" />
        <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="userId" label="用户ID" width="80" />
            <el-table-column label="会话id" width="80">
                <template #default="scope">
                    <el-avatar :size="50">
                        <template #default>
                            {{ scope.row.username }}
                        </template>
                    </el-avatar>
                </template>
            </el-table-column>
            <el-table-column prop="diaryDate" label="记录日期" width="120" />
            <el-table-column label="情绪评分">
                <template #default="scope">
                    <el-rate :model-value="scope.row.moodScore" :max="10" disabled />
                </template>
            </el-table-column>
            <el-table-column label="生活指标" width="120">
                <template #default="scope">
                    <div>
                        <p>睡眠：{{ scope.row.sleepQuality }} / 5</p>
                        <p>压力：{{ scope.row.stressLevel }} / 5</p>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="emotionTriggers" label="情绪触发因素" width="200" />
            <el-table-column prop="diaryContent" label="日记内容" width="120" />
            <el-table-column label="操作" width="200" fixed="right">
                <template #default="scope">
                    <el-button type="primary" text @click="viewEmotionalDetail(scope.row)">详情</el-button>
                    <el-button type="danger" text @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination style="margin-top: 25px;" :page-size="pagination.size" layout="prev, pager, next"
            :total="pagination.total" @change="handleChange" />
        <el-dialog v-model="dialogVisible" title="情绪日记详情" width="800px" :close-on-click-modal="false">
            <div v-if="currentEmotionalDetail" class="detail-content">
                <div class="detail-section">
                    <h4>用户信息</h4>
                    <el-descriptions :column="2" border>
                        <el-descriptions-item label="用户名">{{ currentEmotionalDetail.username }}</el-descriptions-item>
                        <el-descriptions-item label="昵称">{{ currentEmotionalDetail.nickname }}</el-descriptions-item>
                        <el-descriptions-item label="用户ID">{{ currentEmotionalDetail.userId }}</el-descriptions-item>
                        <el-descriptions-item label="记录日期">{{ currentEmotionalDetail.diaryDate }}</el-descriptions-item>
                    </el-descriptions>
                </div>
                <div class="detail-section">
                    <h4>情绪状态</h4>
                    <el-descriptions :column="2" border>
                        <el-descriptions-item label="情绪评分">
                            <el-rate :model-value="currentEmotionalDetail.moodScore" :max="10" disabled />
                        </el-descriptions-item>
                        <el-descriptions-item label="主要情绪">
                            <el-tag :type="getEmotionTagType(currentEmotionalDetail.dominantEmotion)">
                                {{ currentEmotionalDetail.dominantEmotion || '-' }}
                            </el-tag>
                        </el-descriptions-item>
                        <el-descriptions-item label="睡眠质量">
                            {{ currentEmotionalDetail.sleepQuality || '-' }}/5
                        </el-descriptions-item>
                        <el-descriptions-item label="压力水平">
                            {{ currentEmotionalDetail.stressLevel || '-' }}/5
                        </el-descriptions-item>
                    </el-descriptions>
                </div>
                <div class="detail-section">
                    <h4>日记内容</h4>
                    <el-descriptions :column="1" border>
                        <el-descriptions-item label="情绪触发因素">
                            {{ currentEmotionalDetail.emotionTriggers || '无' }}
                        </el-descriptions-item>
                        <el-descriptions-item label="日记内容">
                            {{ currentEmotionalDetail.diaryContent || '无' }}
                        </el-descriptions-item>
                    </el-descriptions>
                </div>
                <div class="detail-section">
                    <h4>AI情绪分析结果</h4>
                    <div class="ai-analysis-result">
                        <div class="ai-description">
                            <el-descriptions :column="2" border>
                                <el-descriptions-item label="主要情绪">
                                    <el-tag :type="getAiEmotionTagType(aiData.primaryEmotion)">
                                        {{ aiData.primaryEmotion }}
                                    </el-tag>
                                </el-descriptions-item>
                                <el-descriptions-item label="情绪强度">
                                    <el-progress :percentage="aiData.emotionScore"
                                        :color="getEmotionScoreColor(aiData.emotionScore)" stroke-width="8" />
                                </el-descriptions-item>
                                <el-descriptions-item label="风险等级">
                                    <el-tag :type="getRiskLevelTagType(aiData.riskLevel)">
                                        {{ getRiskLevelText(aiData.riskLevel) }}
                                    </el-tag>
                                </el-descriptions-item>
                                <el-descriptions-item label="情绪性质">
                                    <el-tag :type="aiData.isNegative ? 'danger' : 'success'">
                                        {{ aiData.isNegative ? '负面情绪' : '正面情绪' }}
                                    </el-tag>
                                </el-descriptions-item>
                            </el-descriptions>
                        </div>
                        <div class="ai-suggestion-section">
                            <h5>专业建议</h5>
                            <div class="suggestion-content">
                                {{ aiData.suggestion || '无' }}
                            </div>
                        </div>
                        <div class="ai-risk-section">
                            <h5>风险描述</h5>
                            <div class="risk-content">
                                {{ aiData.riskDescription || '无' }}
                            </div>
                        </div>
                        <div class="ai-improvements-section">
                            <h5>改进建议</h5>
                            <ul class="improvement-list">
                                <li v-for="item in aiData.improvementSuggestions" :key="item">
                                    {{ item }}
                                </li>
                            </ul>
                        </div>
                        <div class="ai-analysis-meta">
                            <div class="analysis-time">分析时间：{{ formatTimestampUTC(aiData.timestamp) }}</div>
                        </div>
                    </div>
                </div>
                <div class="detail-section">
                    <h4>时间信息</h4>
                    <el-descriptions :column="2" border>
                        <el-descriptions-item label="创建时间">
                            {{ currentEmotionalDetail.createdAt }}
                        </el-descriptions-item>
                        <el-descriptions-item label="更新时间">
                            {{ currentEmotionalDetail.updatedAt }}
                        </el-descriptions-item>
                    </el-descriptions>
                </div>
            </div>
            <template #footer>
                <el-button type="primary" @click="dialogVisible = false">关闭</el-button>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import PageHead from '@/components/PageHead.vue'
import TableSearch from '@/components/TableSearch.vue'
import { getEmotionalLogPage, deleteEmotionalLog } from '@/api/admin'
import { ElMessageBox } from 'element-plus'

const formItem = [
    { comp: 'input', prop: 'userId', label: '用户ID', placeholder: '请输入用户ID' },
    {
        comp: 'select', prop: 'moodScoreRange', label: '情绪评分', placeholder: '请选择评分范围', options: [{
            label: '低分(1-3)',
            value: '1-3'
        }, {
            label: '中分(4-6)',
            value: '4-6'
        }, {
            label: '高分(7-10)',
            value: '7-10'
        }]
    }
]

const pagination = reactive({
    currentPage: 1,
    size: 10,
    total: 0
})

// 情绪日记列表
const tableData = ref([])

/**
 * 搜索
 * @param formData  搜索参数
 */
const handleSearch = async (formData = {}) => {
    const { moodScoreRange, ...searchParams } = formData
    let min = null
    let max = null
    if (moodScoreRange) {
        const range = moodScoreRange.split('-')
        min = Number(range[0])
        max = Number(range[1])
    }

    const params = {
        ...searchParams,
        ...pagination,
        minMoodScore: min,
        maxMoodScore: max
    }
    console.log(params, '查询参数')
    const { records, total } = await getEmotionalLogPage(params)
    console.log(records, '查询结果')
    tableData.value = records
    pagination.total = total
}

const currentEmotionalDetail = ref(null)
const aiData = ref(null)
const viewEmotionalDetail = (row) => {
    dialogVisible.value = true
    currentEmotionalDetail.value = row
    if (row.hasAiEmotionAnalysis) {
        aiData.value = JSON.parse(row.aiEmotionAnalysis)
    } else {
        aiData.value = {}
    }
    console.log(aiData.value, 'AI分析结果')
}

const handleDelete = (row) => {
    ElMessageBox.confirm('确认删除该条情绪日记吗？', '删除确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'danger'
    }).then(() => {
        console.log(row.id, '删除日志')
        deleteEmotionalLog(row.id).then(() => {
            handleSearch()
        })
    })
}

// 分页改变
const handleChange = (page) => {
    pagination.currentPage = page
    handleSearch()
}

// 情绪日记详情弹窗
const dialogVisible = ref(false)

const getEmotionTagType = (emotion) => {
    const emotionTypes = {
        '快乐': 'success',
        '开心': 'success',
        '平静': 'info',
        '兴奋': 'warning',
        '愤怒': 'danger',
        '悲伤': 'info',
        '焦虑': 'warning'
    }
    return emotionTypes[emotion] || 'info'
}

const formatTimestampUTC = (timestamp) => {
    // 空、null、undefined、非数字直接返回空
    if (timestamp === null || timestamp === undefined || timestamp === '' || isNaN(Number(timestamp))) {
        return '-';
    }
    const date = new Date(Number(timestamp));
    // 判断是否是合法时间
    if (isNaN(date.getTime())) {
        return '-';
    }
    const y = date.getUTCFullYear();
    const m = String(date.getUTCMonth() + 1).padStart(2, '0');
    const d = String(date.getUTCDate()).padStart(2, '0');
    const h = String(date.getUTCHours()).padStart(2, '0');
    const min = String(date.getUTCMinutes()).padStart(2, '0');
    const s = String(date.getUTCSeconds()).padStart(2, '0');
    return `${y}-${m}-${d} ${h}:${min}:${s}`;
}

const getAiEmotionTagType = (emotion) => {
    const emotionTagMap = {
        '快乐': 'success',
        '开心': 'success',
        '平静': 'success',
        '兴奋': 'warning',
        '满足': 'success',
        '愤怒': 'danger',
        '悲伤': 'info',
        '焦虑': 'warning',
        '恐惧': 'danger',
        '沮丧': 'info',
        '压力': 'warning'
    }
    return emotionTagMap[emotion] || 'info'
}

const getEmotionScoreColor = (score) => {
    if (score >= 80) return '#f56c6c'
    if (score >= 60) return '#e6a23c'
    if (score >= 40) return '#909399'
    return '#67c23a'
}

const getRiskLevelTagType = (riskLevel) => {
    const riskTagMap = {
        0: 'success',
        1: 'info',
        2: 'warning',
        3: 'danger'
    }
    return riskTagMap[riskLevel] || 'info'
}

const getRiskLevelText = (riskLevel) => {
    const riskTextMap = {
        0: '正常',
        1: '关注',
        2: '预警',
        3: '危机'
    }
    return riskTextMap[riskLevel] || '未知风险等级'
}

// 初始化查询情绪日记
onMounted(() => {
    handleSearch({})
})
</script>

<style scoped lang="scss">
.detail-content {
    .detail-section {
        margin-bottom: 24px;

        h4 {
            margin: 0 0 16px 0;
            color: #303133;
            font-size: 16px;

            i {
                margin-right: 8px;
                color: #409eff;
            }
        }
    }
}

// AI分析相关样式
.ai-analysis-status {
    .ai-status-tag {
        margin-bottom: 4px;

        i {
            margin-right: 4px;
        }
    }

    .ai-analysis-preview {
        font-size: 11px;
        color: #909399;
        margin-top: 2px;
    }
}

.ai-analysis-result {

    .ai-keywords-section,
    .ai-suggestion-section,
    .ai-risk-section,
    .ai-improvements-section {
        margin-top: 16px;
        padding: 12px;
        background-color: #f8f9fa;
        border-radius: 4px;

        h5 {
            margin: 0 0 8px 0;
            color: #606266;
            font-size: 14px;
            font-weight: 600;

            i {
                margin-right: 6px;
                color: #909399;
            }
        }
    }

    .keywords-container {
        display: flex;
        flex-wrap: wrap;
        gap: 6px;

        .keyword-tag {
            background-color: #e1f3d8;
            color: #67c23a;
            border-color: #b3d8a4;
        }
    }

    .suggestion-content,
    .risk-content {
        line-height: 1.6;
        color: #606266;
        background-color: white;
        padding: 8px;
        border-radius: 4px;
        border: 1px solid #ebeef5;
    }

    .improvement-list {
        margin: 0;
        padding-left: 20px;

        li {
            margin-bottom: 4px;
            color: #606266;
            line-height: 1.5;
        }
    }

    .ai-analysis-meta {
        margin-top: 16px;
        padding-top: 12px;
        border-top: 1px solid #ebeef5;

        .analysis-time {
            margin: 0;
            font-size: 12px;
            color: #909399;

            i {
                margin-right: 4px;
            }
        }
    }

    .el-progress {
        .el-progress__text {
            font-size: 12px !important;
        }
    }
}
</style>
