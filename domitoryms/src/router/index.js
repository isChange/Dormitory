import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../views/Login";
import SystemAdmin from "../views/SystemAdmin";
import DormitoryAdminAdd from "../views/DormitoryAdminAdd";
import DormitoryAdminManager from "../views/DormitoryAdminManager";
import DormitoryAdminUpdate from "../views/DormitoryAdminUpdate";
import DormitoryUpdate from "../views/DormitoryUpdate";
import StudentAdd from "../views/StudentAdd";
import StudentManager from "../views/StudentManager";
import StudentUpdate from "../views/StudentUpdate";
import BuildingAdd from "../views/BuildingAdd";
import BuildingManager from "../views/BuildingManager";
import BuildingUpdate from "../views/BuildingUpdate";
import DormitoryAdd from "../views/DormitoryAdd";
import DormitoryManager from "../views/DormitoryManager";
import MoveOutRegister from "../views/MoveOutRegister";
import MoveRecord from "../views/MoveRecord";
import AbsentRecord from "../views/AbsentRecord";
import AbsentRegister from "../views/AbsentRegister";
import DormitoryAdmin from "../views/DormitoryAdmin";


Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name:'登入',
    component: Login
  },{
    path: '/DormitoryAdmin',
    component: DormitoryAdmin,
    children: [
      {
        path: '/absentRecord2',
        component: AbsentRecord
      },
      {
        path: '/absentRegister',
        component: AbsentRegister
      }
    ]
  },
  {
    path: '/SystemAdmin',
    name: '系统管理员',
    component: SystemAdmin,
    children:[
      {
        path: '/dormitoryAdminAdd',
        name: '添加宿管',
        component: DormitoryAdminAdd
      },{
        path: '/dormitoryAdminManager',
        name: '宿管管理',
        component: DormitoryAdminManager
      },{
        path: '/dormitoryAdminUpdate',
        component: DormitoryAdminUpdate
      },{
        path: '/studentAdd',
        component: StudentAdd
      },{
        path: '/studentManager',
        component: StudentManager
      },
      {
        path: '/studentUpdate',
        component: StudentUpdate
      },
      {
        path: '/buildingAdd',
        component: BuildingAdd
      },
      {
        path: '/buildingManager',
        component: BuildingManager
      },
      {
        path: '/buildingUpdate' ,
        component: BuildingUpdate
      },
      {
        path: '/dormitoryAdd',
        component: DormitoryAdd
      },{
        path: '/dormitoryManager',
        component: DormitoryManager
      },
      {
        path: '/dormitoryUpdate',
        component: DormitoryUpdate
      },
      {
        path: '/moveOutRegister',
        component: MoveOutRegister
      }
      ,
      {
        path: '/moveOutRecord',
        component: MoveRecord
      },
      {
        path: '/absentRecord',
        component: AbsentRecord
      }
    ]

  }
]

const router = new VueRouter({
    mode : 'history',
  routes

})

export default router
