<template>
    <div class="backend-layout">
        <el-container class="main-container">
            <Sidebar />
            <el-container>
                <el-header>
                    <Navbar />
                </el-header>
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
<script setup>
import Sidebar from './Sidebar.vue'
import Navbar from './Navbar.vue'
</script>
<style lang="scss" scoped>
.backend-layout {
    height: 100vh;

    .main-container {
        height: 100%;
    }
}
</style>
