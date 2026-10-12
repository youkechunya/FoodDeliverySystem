async function request(url, options = {}) {
    const token = localStorage.getItem('token');
    const config = {
        headers: {
            'Content-Type': 'application/json',
            ...(token ? { 'Authorization': 'Bearer ' + token } : {})
        },
        ...options
    };
    const res = await fetch(url, config);
    if (!res.ok) {
        throw new Error('请求失败：' + res.status);
    }
    const contentType = res.headers.get('content-type') || '';
    if (contentType.includes('application/json')) {
        return res.json();
    }
    return res.text();
}

async function doLogout() {
    try {
        await request('/api/auth/logout', { method: 'POST' });
    } catch (e) {}
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('roleId');
    localStorage.removeItem('roleName');
    localStorage.removeItem('account');
    window.location.href = '/auth/login';
}

function initUserInfo() {
    const account = localStorage.getItem('account') || '';
    const roleName = localStorage.getItem('roleName') || '';
    const el = document.getElementById('userInfo');
    if (!el) return;
    el.textContent = account && roleName ? account + ' · ' + roleName : (account || '未登录');
}

function renderPagination(container, pageNum, pages, onPageChange) {
    container.innerHTML = '';
    if (pages <= 0) return;

    if (pages === 1) {
        container.innerHTML = `<span style="font-size:13px;color:#94a3b8;">第 1 / 1 页</span>`;
        return;
    }

    for (let i = 1; i <= pages; i++) {
        const btn = document.createElement('button');
        btn.textContent = i;
        if (i === pageNum) btn.classList.add('active');
        btn.onclick = () => onPageChange(i);
        container.appendChild(btn);
    }
}

function openModal(id) {
    document.getElementById(id).classList.add('show');
}

function closeModal(id) {
    document.getElementById(id).classList.remove('show');
}

function toast(msg) {
    const el = document.createElement('div');
    el.textContent = msg;
    el.style.cssText = `
        position: fixed; top: 20px; left: 50%; transform: translateX(-50%);
        background: #0f172a; color: #fff; padding: 10px 20px; border-radius: 8px;
        font-size: 14px; z-index: 999; opacity: 0; transition: opacity .3s;
    `;
    document.body.appendChild(el);
    requestAnimationFrame(() => el.style.opacity = '1');
    setTimeout(() => {
        el.style.opacity = '0';
        setTimeout(() => el.remove(), 300);
    }, 2000);
}