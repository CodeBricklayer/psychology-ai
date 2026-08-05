<template>
    <div>
        <PageHead title="知识文章">
            <template #buttons>
                <el-button type="primary" @click="handleEdit({})">新增</el-button>
            </template>
        </PageHead>
        <TableSearch :formItem="formItem" @search="handleSearch" />
        <el-table :data="tableData" style="width: 100%;margin-top: 25px;">
            <el-table-column label="文章标题" fixed="left" width="400">
                <template #default="scope">
                    <div style="display: flex;align-items: center;">
                        <el-icon>
                            <timer />
                        </el-icon>
                        <span>{{ scope.row.title }}</span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="分类" width="200">
                <template #default="scope">
                    <div style="display: flex;align-items: center;">
                        <el-icon>
                            <timer />
                        </el-icon>
                        <span>{{ categoryMap[scope.row.categoryId] }}</span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="authorName" label="作者" , width="150" />
            <el-table-column prop="readCount" label="阅读量" , width="150" />
            <el-table-column prop="updatedAt" label="修改时间" , width="200" />
            <el-table-column prop="statusText" label="状态" , width="150" />
            <el-table-column label="操作" width="240" fixed="right">
                <template #default="scope">
                    <el-button text type="primary" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button @click="handlePublish(scope.row)" text
                        v-if="scope.row.status === 0 || scope.row.status === 2" type="success">发布</el-button>
                    <el-button @click="handleUnpublish(scope.row)" text v-if="scope.row.status === 1"
                        type="warning">下线</el-button>
                    <el-button @click="handleDelete(scope.row)" text type="danger">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination style="margin-top: 25px;" :page-size="pagination.size" layout="prev, pager, next"
            :total="pagination.total" @change="handleChange" />
        <ArticleDialog v-model:modelValue="dialogVisible" :article="currentArticle" :categories="categories"
            @success="handleSuccess" />
    </div>
</template>
<script setup>
import { onMounted, reactive, ref } from 'vue'
import PageHead from '@/components/PageHead.vue'
import TableSearch from '@/components/TableSearch.vue'
import { getCategoryTree, getKnowledgeArticlePage, getArticleDetail, updateKnowledgeArticleStatus, deleteKnowledgeArticle } from '@/api/admin'
import ArticleDialog from '@/components/ArticleDialog.vue'
import { ElMessageBox, ElMessage } from 'element-plus'

const formItem = [
    { comp: 'input', prop: 'title', label: '文章标题', placeholder: '请输入文章标题' },
    { comp: 'select', prop: 'categoryId', label: '分类', placeholder: '请选择分类' },
    {
        comp: 'select', prop: 'status', label: '状态', placeholder: '请选择状态', options: [{
            label: '草稿',
            value: '0'
        }, {
            label: '已发布',
            value: '1'
        }, {
            label: '已下线',
            value: '2'
        }]
    }
]

// 分页参数
const pagination = reactive({
    currentPage: 1,
    size: 10,
    total: 0
})

// 分页改变
const handleChange = (page) => {
    pagination.currentPage = page
    handleSearch()
}

// 分类映射
const categoryMap = reactive({})
// 分类选项
const categories = ref([])

// 文章列表
const tableData = ref([])

const handleSearch = async (formData) => {
    const params = {
        ...formData,
        ...pagination
    }
    console.log(params, '查询参数')
    const { records, total } = await getKnowledgeArticlePage(params)
    console.log(records, '查询结果')
    tableData.value = records
    pagination.total = total
}

// 新增和编辑
const dialogVisible = ref(false)

// 新增和编辑成功后刷新列表
const handleSuccess = () => {
    dialogVisible.value = false
    // 刷新列表
    handleSearch()
}

const currentArticle = ref(null)

const handleEdit = (row) => {
    if (!row.id) {
        // 新增
        currentArticle.value = null
        dialogVisible.value = true
    } else {
        // 编辑
        getArticleDetail(row.id).then(res => {
            currentArticle.value = res
            dialogVisible.value = true
        })
    }
}

const handlePublish = (row) => {
    ElMessageBox.confirm(`确认发布文章${row.title}吗？`, '确认发布', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
    }).then(() => {
        updateKnowledgeArticleStatus(row.id, { status: 1 }).then(res => {
            ElMessage.success('发布成功')
            handleSearch()
        })
    })
}

const handleUnpublish = (row) => {
    ElMessageBox.confirm(`确认下线文章${row.title}吗？`, '确认下线', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        updateKnowledgeArticleStatus(row.id, { status: 2 }).then(res => {
            ElMessage.success('下线成功')
            handleSearch()
        })
    })
}

const handleDelete = (row) => {
    ElMessageBox.confirm(`确认删除文章${row.title}吗？`, '确认删除', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'danger'
    }).then(() => {
        deleteKnowledgeArticle(row.id).then(res => {
            ElMessage.success('删除成功')
            handleSearch()
        })
    })
}

onMounted(async () => {
    const data = await getCategoryTree()
    categories.value = data.map(item => {
        categoryMap[item.id] = item.categoryName
        return {
            label: item.categoryName,
            value: item.id
        }
    })

    formItem[1].options = categories.value

    // 获取列表
    handleSearch()
})
</script>