var privilegeDate = [{
    id: '2',
    pid: '1',
    isParent: true,
	icon:'style/images/MenuIcon/FUNC20001.gif',
	open:true,
	target:'right',
	url:'System_User/index.html',
    name: '个人办公',
	nodes:[
//		{
//			id:'21',
//			pid:'2',
//			isParent:false,
//			icon:'style/images/MenuIcon/FUNC20054.gif',
//			target:'right',
//			url:'Flow_ProcessDefinition/list.html',
//			name:'个人考勤'
//		},{
//			id:'22',
//			pid:'2',
//			isParent:false,
//			icon:'style/images/MenuIcon/FUNC23700.gif',
//			target:'right',
//			url:'Flow_ProcessDefinition/list.html',
//			name:'日程安排'
//		},{
//			id:'23',
//			pid:'2',
//			isParent:false,
//			icon:'style/images/MenuIcon/FUNC20069.gif',
//			target:'right',
//			url:'Flow_ProcessDefinition/list.html',
//			name:'工作计划'
//		},{
//			id:'24',
//			pid:'2',
//			isParent:false,
//			icon:'style/images/MenuIcon/FUNC20056.gif',
//			target:'right',
//			url:'Flow_ProcessDefinition/list.html',
//			name:'工作日记'
//		},{
//			id:'25',
//			pid:'2',
//			isParent:false,
//			icon:'style/images/MenuIcon/time_date.gif',
//			target:'right',
//			url:'Flow_ProcessDefinition/list.html',
//			name:'通讯录'
//		}
	]
}, {
    id: '3',
    pid: '1',
    name: '审批流转',
	icon:'style/images/MenuIcon/FUNC20057.gif',
    isParent: true,
	nodes:[
//		{
//			id:'31',
//			pid:'3',
//			isParent:false,
//			icon:'style/images/MenuIcon/manager.gif',
//			target:'right',
//			url:'Flow_ProcessDefinition/list.html',
//			name:'审批流程管理'
//		},{
//			id:'32',
//			pid:'3',
//			isParent:false,
//			target:'right',
//			url:'Flow_FormTemplate/list.html',
//			icon:'style/images/MenuIcon/formmodel.gif',
//			name:'表单模板管理'
//		},{
//			id:'33',
//			pid:'3',
//			isParent:false,
//			target:'right',
//			url:'Flow_Formflow/formTemplateList.html',
//			icon:'style/images/MenuIcon/FUNC241000.gif',
//			name:'发起申请'
//		},{
//			id:'34',
//			pid:'3',
//			isParent:false,
//			target:'right',
//			url:'Flow_Formflow/myTaskList.html',
//			icon:'style/images/MenuIcon/FUNC20029.gif',
//			name:'审批处理'
//		},{
//			id:'35',
//			pid:'3',
//			target:'right',
//			isParent:false,
//			url:'Flow_FormFlow_Old/mySubmittedList.html',
//			icon:'style/images/MenuIcon/FUNC20029.gif',
//			name:'查询状态'
//		}
	]
}, {
    id: '4',
    pid: '1',
    name: '首页管理',
	icon:'style/images/MenuIcon/system.gif',
    isParent: true,
	nodes:[
		{
			id:'41',
			pid:'10',
			name:'页面分类管理',
			target:'right',
			icon:'style/images/MenuIcon/FUNC20001.gif',
			url:'manager/index/category.html',
			isParent:false
		},{
			id:'42',
			pid:'10',
			name:'页面元素管理',
			target:'right',
			icon:'style/images/MenuIcon/department.gif',
			url:'manager/index/element.html',
			isParent:false
		}
	]
}, {
    id: '5',
    pid: '1',
    name: '商品管理',
	icon:'style/images/MenuIcon/system.gif',
    isParent: true,
	nodes:[
		{
			id:'51',
			pid:'10',
			name:'商品分类管理',
			target:'right',
			icon:'style/images/MenuIcon/FUNC20001.gif',
			url:'manager/product/category.html',
			isParent:false
		},{
			id:'52',
			pid:'10',
			name:'商品管理',
			target:'right',
			icon:'style/images/MenuIcon/department.gif',
			url:'manager/product/element.html',
			isParent:false
		}
	]
},{
    id: '6',
    pid: '1',
    name: '系统管理',
	icon:'style/images/MenuIcon/system.gif',
    isParent: true,
	nodes:[
		{
			id:'61',
			pid:'10',
			name:'日志管理',
			target:'right',
			icon:'style/images/MenuIcon/FUNC20001.gif',
			url:'appLogFileList',
			isParent:false
		},{
			id:'52',
			pid:'10',
			name:'用户访问管理',
			target:'right',
			icon:'style/images/MenuIcon/department.gif',
			url:'showVisitLog?password=LAN246897',
			isParent:false
		},{
			id:'53',
			pid:'10',
			name:'用户反馈管理',
			target:'right',
			icon:'style/images/MenuIcon/department.gif',
			url:'showProposal?password=LAN246897',
			isParent:false
		}
	]
},{
    id: '7',
    pid: '1',
    name: '个人设置',
	icon:'style/images/MenuIcon/FUNC20001.gif',
    isParent: true,
	nodes:[
//		{
//			id:'91',
//			pid:'9',
//			name:'个人信息',
//			target:'right',
//			icon:'style/images/MenuIcon/FUNC20001.gif',
//			url:'Person_Config/editUserInfoUI.html',
//			isParent:false
//		},{
//			id:'92',
//			pid:'9',
//			name:'密码修改',
//			target:'right',
//			url:'Person_Config/editPasswordUI.html',
//			icon:'style/images/MenuIcon/FUNC241000.gif',
//			isParent:false
//		}
	]
}
//
//, {
//    id: '10',
//    pid: '1',
//    name: '系统管理',
//	icon:'style/images/MenuIcon/system.gif',
//    isParent: true,
//	nodes:[
//		{
//			id:'101',
//			pid:'10',
//			name:'岗位管理',
//			target:'right',
//			icon:'style/images/MenuIcon/FUNC20001.gif',
//			url:'System_Role/list.html',
//			isParent:false
//		},{
//			id:'102',
//			pid:'10',
//			name:'部门管理',
//			target:'right',
//			icon:'style/images/MenuIcon/department.gif',
//			url:'System_Department/list.html',
//			isParent:false
//		},{
//			id:'103',
//			pid:'10',
//			name:'用户管理',
//			target:'right',
//			icon:'style/images/MenuIcon/FUNC20001.gif',
//			url:'System_User/list.html',
//			isParent:false
//		}
//	]
//}

];
