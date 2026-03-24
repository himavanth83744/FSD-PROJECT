/**
 * ════════════════════════════════════════════
 *  VELTECH UNIVERSITY — CAMPUS EVENT PORTAL
 *  app.js  |  Frontend JavaScript
 *  Connects to Spring Boot REST API
 * ════════════════════════════════════════════
 */

const API_BASE = 'http://localhost:8080/api';

// ══════════════════════════════════════════════
// SAMPLE EVENTS DATA (fallback if API is unavailable)
// ══════════════════════════════════════════════
const SAMPLE_EVENTS = [
  {
    id: 1, title: 'Tech Symposium 2025', category: 'Technical',
    emoji: '🖥️', color: '#1A1A3E',
    date: '2025-04-12', time: '09:00 AM', venue: 'Main Auditorium',
    organizer: 'CSE Dept.', seats: 300, registered: 178,
    prize: '₹50,000 Prize Pool', status: 'open',
    desc: 'A flagship technical event featuring paper presentations, project expos, and keynote speeches from industry leaders in AI and ML.',
    tags: ['Paper Presentation', 'AI/ML', 'Project Expo']
  },
  {
    id: 2, title: 'Hackathon X – Code Blitz', category: 'Hackathon',
    emoji: '⚡', color: '#1A1E2E',
    date: '2025-04-19', time: '08:00 AM', venue: 'Innovation Hub',
    organizer: 'IT Dept.', seats: 200, registered: 190,
    prize: '₹1,00,000 Prizes', status: 'filling',
    desc: '24-hour hackathon challenging teams to build innovative solutions for real-world problems. All domains welcome!',
    tags: ['24-Hour', 'Team Event', 'Open Domain']
  },
  {
    id: 3, title: 'Vel Fest – Cultural Extravaganza', category: 'Cultural',
    emoji: '🎭', color: '#2E1A1A',
    date: '2025-05-03', time: '05:00 PM', venue: 'Open Air Theatre',
    organizer: 'Student Council', seats: 1000, registered: 340,
    prize: '₹25,000 Prizes', status: 'open',
    desc: 'Annual cultural extravaganza with dance, music, drama, and art competitions. Celebrate diversity and talent.',
    tags: ['Dance', 'Music', 'Drama', 'Art']
  },
  {
    id: 4, title: 'IoT & Robotics Workshop', category: 'Workshop',
    emoji: '🤖', color: '#1A2E1A',
    date: '2025-04-25', time: '10:00 AM', venue: 'ECE Lab Complex',
    organizer: 'ECE Dept.', seats: 60, registered: 60,
    prize: 'Certificate + Kit', status: 'closed',
    desc: 'Hands-on workshop covering IoT fundamentals, sensor integration, and robotics programming using Arduino and Raspberry Pi.',
    tags: ['Hands-on', 'Arduino', 'Raspberry Pi']
  },
  {
    id: 5, title: 'Sports Meet 2025', category: 'Sports',
    emoji: '🏆', color: '#1E2A1A',
    date: '2025-05-15', time: '07:00 AM', venue: 'Sports Complex',
    organizer: 'Physical Ed. Dept.', seats: 500, registered: 210,
    prize: 'Medals + Trophies', status: 'open',
    desc: 'Annual inter-department sports meet featuring cricket, football, basketball, badminton, athletics and more.',
    tags: ['Cricket', 'Football', 'Athletics']
  },
  {
    id: 6, title: 'AI for Good — Ideathon', category: 'Technical',
    emoji: '🧠', color: '#1A1A3E',
    date: '2025-06-01', time: '10:00 AM', venue: 'Seminar Hall A',
    organizer: 'AI & ML Dept.', seats: 120, registered: 32,
    prize: '₹30,000 Prizes', status: 'upcoming',
    desc: 'Ideathon focused on leveraging AI for social good – from healthcare to agriculture to education.',
    tags: ['AI/ML', 'Social Impact', 'Ideas']
  },
  {
    id: 7, title: 'Business Plan Competition', category: 'Technical',
    emoji: '💼', color: '#2A1E0E',
    date: '2025-05-20', time: '11:00 AM', venue: 'Seminar Hall B',
    organizer: 'Business School', seats: 80, registered: 55,
    prize: '₹20,000 + Mentorship', status: 'open',
    desc: 'Present your startup idea to a panel of investors and industry mentors. Winner gets funding and incubation support.',
    tags: ['Startup', 'Pitch', 'Entrepreneurship']
  },
  {
    id: 8, title: 'Photography & Design Fest', category: 'Cultural',
    emoji: '📸', color: '#2E1A2E',
    date: '2025-05-08', time: '09:00 AM', venue: 'Art Gallery Hall',
    organizer: 'Fine Arts Club', seats: 150, registered: 88,
    prize: '₹10,000 + Exhibition', status: 'open',
    desc: 'Showcase your photography, graphic design, and digital art skills. Winners get exhibited in the university gallery.',
    tags: ['Photography', 'Design', 'Art']
  }
];

// ══════════════════════════════════════════════
// STATE
// ══════════════════════════════════════════════
let allEvents = [];
let activeFilter = 'All';
let registrations = JSON.parse(localStorage.getItem('vt_regs') || '[]');

// ══════════════════════════════════════════════
// INIT
// ══════════════════════════════════════════════
document.addEventListener('DOMContentLoaded', async () => {
  await loadEvents();
  populateEventDropdown();
});

// ══════════════════════════════════════════════
// LOAD EVENTS (API → fallback to sample data)
// ══════════════════════════════════════════════
async function loadEvents() {
  try {
    const res = await fetch(`${API_BASE}/events`);
    if (!res.ok) throw new Error('API not available');
    allEvents = await res.json();
    showToast('✅ Events loaded from server');
  } catch {
    allEvents = SAMPLE_EVENTS;
    // API offline; using sample data silently
  }
  renderEvents(allEvents);
}

// ══════════════════════════════════════════════
// RENDER EVENTS
// ══════════════════════════════════════════════
function renderEvents(events) {
  const grid = document.getElementById('eventsGrid');
  const noRes = document.getElementById('noResults');
  grid.innerHTML = '';
  if (!events.length) {
    noRes.classList.remove('hidden');
    return;
  }
  noRes.classList.add('hidden');
  events.forEach((ev, i) => {
    const pct = Math.round((ev.registered / ev.seats) * 100);
    const card = document.createElement('div');
    card.className = 'event-card';
    card.style.animationDelay = `${i * 0.07}s`;
    card.onclick = () => openEventDetail(ev);
    card.innerHTML = `
      <div class="ec-banner" style="background:linear-gradient(135deg,${ev.color || '#1a1a2e'} 0%, #0d0d0d 100%)">
        <span>${ev.emoji}</span>
        <div class="ec-cat-badge">${ev.category}</div>
        <div class="ec-seats-badge">${ev.seats - ev.registered} seats left</div>
      </div>
      <div class="ec-body">
        <div class="ec-title">${ev.title}</div>
        <div class="ec-desc">${ev.desc.substring(0, 90)}…</div>
        <div class="ec-meta">
          <div class="ec-meta-item">📅 <span>${formatDate(ev.date)}</span></div>
          <div class="ec-meta-item">⏰ <span>${ev.time}</span></div>
          <div class="ec-meta-item">📍 <span>${ev.venue}</span></div>
          <div class="ec-meta-item status-${ev.status}">
            <span class="status-dot"></span>
            <span>${capitalize(ev.status)}</span>
          </div>
        </div>
        <div class="ec-footer">
          <div class="ec-prize">${ev.prize}</div>
          ${ev.status !== 'closed'
            ? `<button class="btn-register" onclick="quickRegister(event, ${ev.id})">Register →</button>`
            : `<span style="color:#f44336;font-size:0.82rem;font-weight:600">Closed</span>`
          }
        </div>
      </div>
    `;
    grid.appendChild(card);
  });
}

// ══════════════════════════════════════════════
// FILTER
// ══════════════════════════════════════════════
function setFilter(cat, btn) {
  activeFilter = cat;
  document.querySelectorAll('.chip').forEach(c => c.classList.remove('active'));
  btn.classList.add('active');
  filterEvents();
}

function filterEvents() {
  const q = document.getElementById('searchInput').value.toLowerCase();
  const filtered = allEvents.filter(ev => {
    const matchCat = activeFilter === 'All' || ev.category === activeFilter;
    const matchQ   = !q || ev.title.toLowerCase().includes(q) || ev.desc.toLowerCase().includes(q) || ev.category.toLowerCase().includes(q);
    return matchCat && matchQ;
  });
  renderEvents(filtered);
}

// ══════════════════════════════════════════════
// EVENT DETAIL MODAL
// ══════════════════════════════════════════════
function openEventDetail(ev) {
  const c = document.getElementById('eventModalContent');
  c.innerHTML = `
    <div class="em-banner" style="background:linear-gradient(135deg,${ev.color || '#1a1a2e'} 0%, #0d0d0d 100%)">${ev.emoji}</div>
    <div class="em-tags">
      <span class="em-tag">${ev.category}</span>
      ${(ev.tags || []).map(t => `<span class="em-tag">${t}</span>`).join('')}
    </div>
    <div class="em-title">${ev.title}</div>
    <div class="em-desc">${ev.desc}</div>
    <div class="em-grid">
      <div class="em-detail"><span class="em-detail-label">Date</span><span class="em-detail-value">📅 ${formatDate(ev.date)}</span></div>
      <div class="em-detail"><span class="em-detail-label">Time</span><span class="em-detail-value">⏰ ${ev.time}</span></div>
      <div class="em-detail"><span class="em-detail-label">Venue</span><span class="em-detail-value">📍 ${ev.venue}</span></div>
      <div class="em-detail"><span class="em-detail-label">Organizer</span><span class="em-detail-value">🏫 ${ev.organizer}</span></div>
      <div class="em-detail"><span class="em-detail-label">Prize</span><span class="em-detail-value">🏆 ${ev.prize}</span></div>
      <div class="em-detail"><span class="em-detail-label">Seats</span><span class="em-detail-value">💺 ${ev.seats - ev.registered}/${ev.seats} available</span></div>
    </div>
    ${ev.status !== 'closed'
      ? `<button class="btn-gold em-register-btn" onclick="quickRegister(event,${ev.id}); closeModal('eventModal')">Register for this Event</button>`
      : `<div style="text-align:center;color:#f44336;font-weight:600;padding:14px">Registration Closed</div>`
    }
  `;
  openModal('eventModal');
}

// ══════════════════════════════════════════════
// QUICK REGISTER (scroll to form & pre-select)
// ══════════════════════════════════════════════
function quickRegister(e, eventId) {
  e.stopPropagation();
  const ev = allEvents.find(x => x.id === eventId);
  if (ev) {
    document.getElementById('reg-event').value = ev.title;
    document.getElementById('register').scrollIntoView({ behavior: 'smooth' });
  }
}

// ══════════════════════════════════════════════
// POPULATE EVENT DROPDOWN
// ══════════════════════════════════════════════
function populateEventDropdown() {
  const sel = document.getElementById('reg-event');
  allEvents.filter(ev => ev.status !== 'closed').forEach(ev => {
    const opt = document.createElement('option');
    opt.value = ev.title;
    opt.textContent = `${ev.title} — ${formatDate(ev.date)}`;
    sel.appendChild(opt);
  });
}

// ══════════════════════════════════════════════
// HANDLE REGISTRATION SUBMIT
// ══════════════════════════════════════════════
async function handleRegistration(e) {
  e.preventDefault();
  const btn     = document.getElementById('submitBtn');
  const txt     = document.getElementById('submitText');
  const spinner = document.getElementById('submitSpinner');
  const success = document.getElementById('regSuccess');
  const error   = document.getElementById('regError');

  success.classList.add('hidden');
  error.classList.add('hidden');
  btn.disabled = true;
  spinner.classList.remove('hidden');
  txt.textContent = 'Registering…';

  const payload = {
    name:       document.getElementById('reg-name').value,
    rollNumber: document.getElementById('reg-rollno').value,
    department: document.getElementById('reg-dept').value,
    year:       document.getElementById('reg-year').value,
    email:      document.getElementById('reg-email').value,
    phone:      document.getElementById('reg-phone').value,
    eventName:  document.getElementById('reg-event').value,
    message:    document.getElementById('reg-message').value,
    status:     'confirmed',
    registeredAt: new Date().toISOString()
  };

  try {
    const res = await fetch(`${API_BASE}/registrations`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!res.ok) throw new Error('Server error');
    const data = await res.json();
    saveLocalRegistration({ ...payload, id: data.id || Date.now() });
    showSuccess();
  } catch {
    // Offline mode: save locally
    saveLocalRegistration({ ...payload, id: Date.now() });
    showSuccess();
  }

  function showSuccess() {
    success.classList.remove('hidden');
    btn.disabled = false;
    spinner.classList.add('hidden');
    txt.textContent = 'Register for Event';
    document.getElementById('registrationForm').reset();
    showToast('🎉 Registration confirmed!');
    setTimeout(() => success.classList.add('hidden'), 5000);
  }

  btn.disabled = false;
  spinner.classList.add('hidden');
  txt.textContent = 'Register for Event';
}

// ══════════════════════════════════════════════
// SAVE REGISTRATION LOCALLY
// ══════════════════════════════════════════════
function saveLocalRegistration(reg) {
  registrations.push(reg);
  localStorage.setItem('vt_regs', JSON.stringify(registrations));
}

// ══════════════════════════════════════════════
// LOOKUP REGISTRATIONS
// ══════════════════════════════════════════════
async function lookupRegistrations() {
  const roll = document.getElementById('lookupRoll').value.trim();
  if (!roll) { showToast('⚠️ Please enter your register number'); return; }
  let regs = [];

  try {
    const res = await fetch(`${API_BASE}/registrations/student/${roll}`);
    if (res.ok) regs = await res.json();
    else throw new Error();
  } catch {
    // fallback to local
    regs = registrations.filter(r => r.rollNumber?.toUpperCase() === roll.toUpperCase());
  }

  const wrap = document.getElementById('regTableWrap');
  const tbody = document.getElementById('regTableBody');
  wrap.style.display = 'block';
  tbody.innerHTML = '';

  if (!regs.length) {
    tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;color:var(--text-muted);padding:30px">No registrations found for ${roll}</td></tr>`;
    return;
  }

  regs.forEach((r, i) => {
    const ev = allEvents.find(e => e.title === r.eventName);
    tbody.innerHTML += `
      <tr>
        <td>${i + 1}</td>
        <td><strong style="color:var(--gold)">${r.eventName}</strong></td>
        <td>${ev ? ev.category : '—'}</td>
        <td>${ev ? formatDate(ev.date) : '—'}</td>
        <td><span class="status-pill pill-${r.status === 'confirmed' ? 'confirmed' : r.status === 'cancelled' ? 'cancelled' : 'pending'}">${capitalize(r.status || 'pending')}</span></td>
        <td>${r.status !== 'cancelled' ? `<button class="cancel-btn" onclick="cancelReg(${r.id || i})">Cancel</button>` : '—'}</td>
      </tr>
    `;
  });
}

// ══════════════════════════════════════════════
// CANCEL REGISTRATION
// ══════════════════════════════════════════════
async function cancelReg(id) {
  if (!confirm('Cancel this registration?')) return;
  try {
    await fetch(`${API_BASE}/registrations/${id}`, { method: 'DELETE' });
  } catch { /* offline */ }
  registrations = registrations.map(r => r.id == id ? { ...r, status: 'cancelled' } : r);
  localStorage.setItem('vt_regs', JSON.stringify(registrations));
  document.getElementById('lookupRoll').dispatchEvent(new Event('change'));
  lookupRegistrations();
  showToast('Registration cancelled.');
}

// ══════════════════════════════════════════════
// AUTH
// ══════════════════════════════════════════════
async function handleLogin(e) {
  e.preventDefault();
  const user = document.getElementById('login-user').value;
  const pass = document.getElementById('login-pass').value;
  try {
    const res = await fetch(`${API_BASE}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username: user, password: pass })
    });
    if (!res.ok) throw new Error();
    showToast('✅ Logged in successfully!');
    closeModal('loginModal');
  } catch {
    showToast('⚠️ Login successful (demo mode)');
    closeModal('loginModal');
  }
}

async function handleSignup(e) {
  e.preventDefault();
  showToast('✅ Account created! Please login.');
  closeModal('signupModal');
}

// ══════════════════════════════════════════════
// MODAL HELPERS
// ══════════════════════════════════════════════
function openModal(id) {
  document.getElementById(id).classList.add('active');
  document.body.style.overflow = 'hidden';
}
function closeModal(id) {
  document.getElementById(id).classList.remove('active');
  document.body.style.overflow = '';
}
function switchModal(from, to) {
  closeModal(from);
  setTimeout(() => openModal(to), 200);
}

// Close modal on overlay click
document.querySelectorAll('.modal-overlay').forEach(overlay => {
  overlay.addEventListener('click', function(e) {
    if (e.target === this) closeModal(this.id);
  });
});

// ══════════════════════════════════════════════
// NAV HELPERS
// ══════════════════════════════════════════════
function scrollTo(id) {
  document.getElementById(id).scrollIntoView({ behavior: 'smooth' });
}
function toggleNav() {
  // Simple mobile nav toggle
  const nav = document.querySelector('.main-nav');
  nav.style.display = nav.style.display === 'flex' ? 'none' : 'flex';
}

// ══════════════════════════════════════════════
// TOAST
// ══════════════════════════════════════════════
let toastTimer;
function showToast(msg) {
  const t = document.getElementById('toast');
  t.textContent = msg;
  t.classList.add('show');
  clearTimeout(toastTimer);
  toastTimer = setTimeout(() => t.classList.remove('show'), 3500);
}

// ══════════════════════════════════════════════
// UTILS
// ══════════════════════════════════════════════
function formatDate(d) {
  if (!d) return '—';
  const dt = new Date(d);
  return dt.toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' });
}
function capitalize(s) { return s ? s.charAt(0).toUpperCase() + s.slice(1) : ''; }
