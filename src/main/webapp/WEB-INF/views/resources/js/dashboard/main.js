let totalSalesChartInstance, sellerSalesChartInstance;
let currentSellerPage = 1;
let currentRankingPage = 1;

// --- 페이지 로딩 및 이벤트 핸들러 설정 ---
$(document).ready(function() {
    initializeDates();
    initializeEventHandlers();
    
    showDashboardSection();
    loadInitialData();
});

// --- 초기화 관련 함수 ---
function initializeDates() {
    const today = new Date();
    const monthAgo = new Date(today);
    monthAgo.setMonth(today.getMonth() - 1);
    const formatDate = (date) => date.toISOString().split('T')[0];
    
    $('#totalSales-startDate, #sellerSales-startDate, #ranking-startDate').val(formatDate(monthAgo));
    $('#totalSales-endDate, #sellerSales-endDate, #ranking-endDate').val(formatDate(today));
}

function initializeEventHandlers() {
    // 조회 버튼
    $('#btn-load-total-sales').on('click', loadTotalSalesChart);
    $('#btn-load-seller-sales').on('click', loadSellerSalesChart);
    $('#btn-load-seller-ranking').on('click', () => {
        currentRankingPage = 1;
        loadSellerRanking();
    });

    // 순위 정렬 버튼
    $('#rankingTable-controls').on('click', '.btn-sort', function() {
        $('#rankingTable-controls .btn-sort').removeClass('active');
        $(this).addClass('active');
        currentRankingPage = 1;
        loadSellerRanking();
    });
    
    // 순위 페이징
    $('#ranking-pagination').on('click', 'button', function() {
        const action = $(this).text();
        if (action === '이전') currentRankingPage--;
        if (action === '다음') currentRankingPage++;
        loadSellerRanking();
    });

    // 판매자 찾기 모달
    $('#btn-open-seller-modal').on('click', openSellerModal);
    $('.close-button').on('click', () => $('#sellerModal').hide());
    $('#sellerModal').on('click', '#btn-seller-search', searchSellers);
    $('#sellerModal').on('keypress', '#seller-search-input', e => {
        if (e.which === 13) searchSellers();
    });

    // 네비게이션 탭 이동
    $(window).on('hashchange', showDashboardSection);
}

function loadInitialData() {
    loadTotalSalesChart();
    loadSellerSalesChart();
    loadSellerRanking();
}


// --- 탭 기능 함수 ---
function showDashboardSection() {
    const hash = window.location.hash || '#total-sales';
    $('.dashboard-section').addClass('hidden');
    $(hash).removeClass('hidden');

    $('.nav-item.dropdown .dropdown-item').each(function() {
        $(this).toggleClass('active', $(this).attr('href').endsWith(hash));
    });
}


// --- AJAX 호출 및 렌더링 함수 ---

function loadTotalSalesChart() {
    const startDate = $('#totalSales-startDate').val();
    const endDate = $('#totalSales-endDate').val();
    const groupBy = $('#totalSales-groupBy').val();

    $.ajax({
        url: '/dashboard/stats/total-sales',
        data: { startDate, endDate, groupBy },
        success: function(data) {
            if (!data) return;
            let labels = data.map(d => d.label);
            const salesAmounts = data.map(d => d.salesAmount);
            const salesCounts = data.map(d => d.salesCount);

            if (groupBy === 'week') {
                labels = labels.map(label => {
                    try {
                        const year = parseInt(label.substring(0, 4));
                        const weekOfYear = parseInt(label.substring(6, 8));
                        if(isNaN(year) || isNaN(weekOfYear)) return label;
                        
                        const d = new Date(year, 0, 1 + (weekOfYear - 1) * 7);
                        const dayOfWeek = d.getDay() || 7;
                        d.setDate(d.getDate() - dayOfWeek + 4);
                        
                        const month = d.getMonth() + 1;
                        const firstDayOfMonth = new Date(d.getFullYear(), d.getMonth(), 1);
                        const weekOfMonth = Math.ceil((d.getDate() + firstDayOfMonth.getDay()) / 7);

                        return `${month}월 ${weekOfMonth}주차`;
                    } catch(e) { return label; }
                });
            }

            const ctx = document.getElementById('totalSalesChart').getContext('2d');
            if(totalSalesChartInstance) totalSalesChartInstance.destroy();
            
            totalSalesChartInstance = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [
                        { label: '총 매출액', data: salesAmounts, backgroundColor: 'rgba(54, 162, 235, 0.6)', yAxisID: 'yAmount' },
                        { label: '판매 건수', data: salesCounts, type: 'line', borderColor: 'rgba(255, 99, 132, 1)', tension: 0.1, yAxisID: 'yCount' }
                    ]
                },
                options: { scales: { yAmount: { position: 'left', title: { display: true, text: '매출액 (원)' } }, yCount: { position: 'right', title: { display: true, text: '판매 건수 (건)' }, grid: { drawOnChartArea: false } } } }
            });
        }
    });
}

function loadSellerSalesChart() {
    const hostId = $('#selected-host-id').val();
    if (!hostId) {
        if(sellerSalesChartInstance) sellerSalesChartInstance.destroy();
        return;
    }
    const startDate = $('#sellerSales-startDate').val();
    const endDate = $('#sellerSales-endDate').val();
    const groupBy = $('#sellerSales-groupBy').val();

    $.ajax({
        url: '/dashboard/stats/seller-sales',
        data: { hostId, startDate, endDate, groupBy },
        success: function(data) {
            if (!data) return;
            
            let labels = data.map(d => d.label);
            const salesAmounts = data.map(d => d.salesAmount);
            const salesCounts = data.map(d => d.salesCount);
            const avgRatings = data.map(d => d.avgRating);

            if (groupBy === 'week') {
                labels = labels.map(label => {
                    try {
                        const year = parseInt(label.substring(0, 4));
                        const weekOfYear = parseInt(label.substring(6, 8));
                        if(isNaN(year) || isNaN(weekOfYear)) return label;
                        
                        const d = new Date(year, 0, 1 + (weekOfYear - 1) * 7);
                        const dayOfWeek = d.getDay() || 7;
                        d.setDate(d.getDate() - dayOfWeek + 4);
                        
                        const month = d.getMonth() + 1;
                        const firstDayOfMonth = new Date(d.getFullYear(), d.getMonth(), 1);
                        const weekOfMonth = Math.ceil((d.getDate() + firstDayOfMonth.getDay()) / 7);

                        return `${month}월 ${weekOfMonth}주차`;
                    } catch(e) { return label; }
                });
            }

            const ctx = document.getElementById('sellerSalesChart').getContext('2d');
            if(sellerSalesChartInstance) sellerSalesChartInstance.destroy();
            
            sellerSalesChartInstance = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [
                        { label: '매출액', data: salesAmounts, backgroundColor: 'rgba(75, 192, 192, 0.6)', yAxisID: 'yAmount' },
                        { label: '판매 건수', data: salesCounts, type: 'line', borderColor: 'rgba(255, 159, 64, 1)', tension: 0.1, yAxisID: 'yCount' },
                        { label: '평균 평점', data: avgRatings, type: 'line', borderColor: 'rgba(153, 102, 255, 1)', tension: 0.1, yAxisID: 'yRating' }
                    ]
                },
                options: {
                    scales: {
                        yAmount: { position: 'left', title: { display: true, text: '매출액 (원)' } },
                        yCount: { position: 'right', title: { display: true, text: '판매 건수 (건)' }, grid: { drawOnChartArea: false } },
                        yRating: { type: 'linear', display: false, position: 'right', min: 0, max: 5 }
                    }
                }
            });
        }
    });
}

function loadSellerRanking() {
    const startDate = $('#ranking-startDate').val();
    const endDate = $('#ranking-endDate').val();
    const orderBy = $('#rankingTable-controls .btn-sort.active').data('orderby');

    $.ajax({
        url: '/dashboard/stats/seller-ranking',
        data: { startDate, endDate, orderBy, page: currentRankingPage },
        success: function(response) {
            if (!response) return;
            const { rankingList, paging } = response;
            currentRankingPage = paging.curPage;

            const tableBody = $('#rankingTable tbody');
            tableBody.empty();
            rankingList.forEach(item => {
                tableBody.append(`<tr><td>${item.rank}</td><td>${item.hostId}</td><td>${item.hostName}</td><td>${item.totalAmount.toLocaleString()} 원</td><td>${item.totalCount} 건</td><td>⭐ ${item.averageRating.toFixed(1)}</td><td>${item.reviewCount} 건</td></tr>`);
            });
            renderRankingPagination(paging);
        }
    });
}

function openSellerModal() {
    $('#sellerModal').show();
    currentSellerPage = 1;
    $('#seller-search-input').val('');
    loadSellersForModal('');
}

function searchSellers() {
    currentSellerPage = 1;
    loadSellersForModal($('#seller-search-input').val());
}

function loadSellersForModal(query) {
    $.ajax({
        url: '/dashboard/api/sellers',
        data: { query: query, page: currentSellerPage },
        success: function(response) {
            if (!response) return;
            const { sellers, paging } = response;
            currentSellerPage = paging.curPage;

            const tableBody = $('#seller-list-table tbody');
            tableBody.empty();
            if (sellers && sellers.length > 0) {
                sellers.forEach(seller => {
                    tableBody.append(`<tr><td>${seller.userId}</td><td>${seller.userName}</td><td><button onclick="selectSeller('${seller.userId}', '${seller.userName}')">선택</button></td></tr>`);
                });
            } else {
                tableBody.append(`<tr><td colspan="3" style="text-align:center;">검색 결과가 없습니다.</td></tr>`);
            }
            renderSellerPagination(paging);
        }
    });
}

function renderRankingPagination(paging) {
    const paginationDiv = $('#ranking-pagination');
    paginationDiv.empty();
    if (!paging || paging.totalPage <= 1) return;
    
    const prevButton = $('<button>이전</button>').prop('disabled', paging.curPage <= 1);
    const nextButton = $('<button>다음</button>').prop('disabled', paging.curPage >= paging.totalPage);
    
    paginationDiv.append(prevButton)
                 .append(`<span>${paging.curPage} / ${paging.totalPage}</span>`)
                 .append(nextButton);
}

function renderSellerPagination(paging) {
    const paginationDiv = $('#seller-pagination');
    paginationDiv.empty();
    if (!paging || paging.totalPage <= 1) return;

    const prevButton = $('<button>이전</button>').prop('disabled', paging.curPage <= 1);
    const nextButton = $('<button>다음</button>').prop('disabled', paging.curPage >= paging.totalPage);

    paginationDiv.append(prevButton)
                 .append(`<span>${paging.curPage} / ${paging.totalPage}</span>`)
                 .append(nextButton);
}

function selectSeller(hostId, hostName) {
    $('#selected-host-id').val(hostId);
    $('#selected-host-name').val(hostId);
    $('#sellerModal').hide();
    loadSellerSalesChart();
}