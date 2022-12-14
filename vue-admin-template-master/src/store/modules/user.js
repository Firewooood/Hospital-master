import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: []
  },

  // 更改users 中的状态的唯一方法是 提交 mutations, mutations 相当于 事件注册 需要相应的条件触发
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    }
  },

  actions: {  // 管理触发条件,触发mutations  调用此处的代码,需要用到dispatch
    // 登录
 /*   Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(response => {
          const data = response.data
          setToken(data.token)
          commit('SET_TOKEN', data.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },*/
			  Login({ commit },userInfo) {
			const data = {'token': 'admin'}
			setToken(data.token)// 将token存储在cookie中
			commit('SET_TOKEN', data.token)  // 调用context.commit 提交一个mutation SET_TOKEN
			},

    
    
    // 获取用户信息
  /*  GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.token).then(response => {
          const data = response.data
          if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', data.roles)
          } else {
            reject('getInfo: roles must be a non-null array !')
          }
          commit('SET_NAME', data.name)
          commit('SET_AVATAR', data.avatar)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },*/
   
		  GetInfo({ commit,state }) {
			const data = {'roles':'admin',
			'name': 'admin',
			'avatar': 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif'
			}
			if (data.roles && data.roles.length >0) { // 验证返回的roles是否是一个非空数组
			commit('SET_ROLES', data.roles)
			  }else {
			  	reject('getInfo: roles must be a non-null array !')
			  }
			  commit('SET_NAME', data.name)
			  commit('SET_AVATAR', data.avatar)
			},
			   
   

    // 登出
/*    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
*/

			LogOut({ commit, state }) {
		return new Promise((resolve, reject) => {
		    commit('SET_TOKEN', '')
		    commit('SET_ROLES', [])
		    removeToken()
		    resolve()
		  })
		},




    // 前端 登出
   /* FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }*/
   
    FedLogOut({ commit }) {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
    }
   
   
   
  }
}

export default user
