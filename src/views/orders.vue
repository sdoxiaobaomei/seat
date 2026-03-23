<template>
    <div class="booking-records">
        <el-row v-for="(group, index) in groupedRecords" :key="index" class="group-section">
            <el-col :span="24">
                <el-card v-for="(record, idx) in group.records" :key="idx" class="record-card">
                    <div class="group-title">{{ group.date }}</div>
                    <div class="card-header">
                        <span class="seat-info">座位: {{ record.seat }}</span>
                        <span class="status" :class="record.statusClass">{{ record.status }}</span>
                    </div>
                    <div class="card-content">
                        <p>订阅人: {{ record.username }}</p>
                        <p>订阅时间: {{ formatDate(record.bookingTime) }}</p>
                        <p>状态: <strong>{{ record.status }}</strong></p>
                    </div>
                    <div class="card-actions">
                        <el-button v-if="record.status === '已订阅'" type="danger" size="mini" @click="cancelBooking(record)">取消订阅</el-button>
                        <el-button v-else type="primary" size="mini" @click="viewDetails(record)">查看详情</el-button>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
  </template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import { ElMessage } from 'element-plus';

ElMessage({
    message: `该页面未实装，仅供展示`,
    type: 'warning',
});

// 假设seatBookings是从API获取的数据
const seatBookings = ref([
  {
    id: 1,
    seat: 'A1',
    username: 'user1',
    bookingTime: '2024-09-21 10:00',
    status: '已订阅',
    date: '2024-09-21',
  },
  {
    id: 2,
    seat: 'B2',
    username: 'user2',
    bookingTime: '2024-09-22 12:00',
    status: '已取消',
    date: '2024-09-22',
  },
  // 更多记录...
]);

// 将记录按日期分组
const groupedRecords = computed(() => {
  const groups: Record<string, any> = {};
  seatBookings.value.forEach(record => {
    if (!groups[record.date]) {
      groups[record.date] = [];
    }
    groups[record.date].push(record);
  });

  return Object.keys(groups).map(date => ({
    date,
    records: groups[date],
  }));
});

// 格式化时间
function formatDate(dateString: string) {
  const date = new Date(dateString);
  return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date
    .getDate()
    .toString()
    .padStart(2, '0')} ${date.getHours()}:${date.getMinutes().toString().padStart(2, '0')}`;
}

// 取消订阅
const cancelBooking = (record) => {
  // 调用取消订阅的API
  ElMessage({
    message: `订阅座位 ${record.seat} 已取消`,
    type: 'success',
  });
};

// 查看详情
const viewDetails = (record) => {
  // 跳转到详情页面或弹出详细信息
  ElMessage({
    message: `查看座位 ${record.seat} 的详情`,
    type: 'info',
  });
};
</script>

<style scoped>
.booking-records {
  padding: 16px;
}
.group-section {
  margin-bottom: 16px;
}
.group-title {
  font-weight: bold;
  font-size: 1.2em;
  margin-bottom: 8px;
}
.record-card {
  margin-bottom: 12px;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  padding: 10px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  font-size: 1em;
  margin-bottom: 6px;
}
.card-content p {
  margin: 4px 0;
}
.card-actions {
  display: flex;
  justify-content: flex-end;
}
.status {
  padding: 2px 8px;
  border-radius: 4px;
  font-weight: bold;
}
.status.已订阅 {
  background-color: #67c23a;
  color: white;
}
.status.已取消 {
  background-color: #f56c6c;
  color: white;
}
</style>