import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import Benchmarks from '../components/Benchmarks.vue';
import BenchmarkDetail from '../components/BenchmarkDetail.vue';
import BenchmarkConstituents from '../components/BenchmarkConstituents.vue';
import BenchmarkConstituentDetail from '../components/BenchmarkConstituentDetail.vue';
import BenchmarkValues from '../components/BenchmarkValues.vue';
import BenchmarkValueDetail from '../components/BenchmarkValueDetail.vue';
import PortfolioBenchmarks from '../components/PortfolioBenchmarks.vue';
import PortfolioBenchmarkDetail from '../components/PortfolioBenchmarkDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/benchmarks',
    name: 'Benchmarks',
    component: Benchmarks
  },
  {
    path: '/benchmark/:id',
    name: 'BenchmarkDetail',
    component: BenchmarkDetail,
    props: true
  },
  {
    path: '/benchmarkConstituents',
    name: 'BenchmarkConstituents',
    component: BenchmarkConstituents
  },
  {
    path: '/benchmarkConstituent/:id',
    name: 'BenchmarkConstituentDetail',
    component: BenchmarkConstituentDetail,
    props: true
  },
  {
    path: '/benchmarkValues',
    name: 'BenchmarkValues',
    component: BenchmarkValues
  },
  {
    path: '/benchmarkValue/:id',
    name: 'BenchmarkValueDetail',
    component: BenchmarkValueDetail,
    props: true
  },
  {
    path: '/portfolioBenchmarks',
    name: 'PortfolioBenchmarks',
    component: PortfolioBenchmarks
  },
  {
    path: '/portfolioBenchmark/:id',
    name: 'PortfolioBenchmarkDetail',
    component: PortfolioBenchmarkDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
