import request from '@/utils/request'  //可以理解为导入的公共包

const api_name = '/admin/cmn/dict' //从java对应的controller复制过来！


export default {
  dictList(id) {//数据字典列表
    return request ({
      url: `${api_name}/findChildData/${id}`,
      method: 'get'
    })
  }
}