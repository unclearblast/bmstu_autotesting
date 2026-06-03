import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '30s', target: 50 },   // разгон до 50 пользователей
    { duration: '60s', target: 50 },   // удержание 50 пользователей
    { duration: '30s', target: 0 },    // спад до 0
  ],
  thresholds: {
    http_req_failed: ['rate<0.05'],    // ошибки не более 5%
    http_req_duration: ['p(95)<500'],  // 95% запросов быстрее 500ms
  },
};

const BASE_URL = 'http://localhost:8080';

export default function () {
  // 1. Создание спутника
  const createPayload = JSON.stringify({
    name: `TestSat-${__VU}-${__ITER}`,
    state: 'ACTIVE'
  });
  const createRes = http.post(`${BASE_URL}/satellites`, createPayload, {
    headers: { 'Content-Type': 'application/json' },
  });
  check(createRes, { 'create status 201': (r) => r.status === 201 });
  
  let satelliteId = null;
  try {
    satelliteId = createRes.json().id;
  } catch (e) {
    console.error('Failed to parse id from creation response');
    return;
  }
  
  // 2. Получение списка спутников
  const listRes = http.get(`${BASE_URL}/satellites`);
  check(listRes, { 'list status 200': (r) => r.status === 200 });
  
  // 3. Получение деталей спутника
  if (satelliteId) {
    const getRes = http.get(`${BASE_URL}/satellites/${satelliteId}`);
    check(getRes, { 'get status 200': (r) => r.status === 200 });
  }
  
  // 4. Удаление спутника
  if (satelliteId) {
    const delRes = http.del(`${BASE_URL}/satellites/${satelliteId}`);
    check(delRes, { 'delete status 204': (r) => r.status === 204 });
  }
  
  sleep(1); // пауза между итерациями
}
