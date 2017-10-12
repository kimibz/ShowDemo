package com.xigua.JSONTemplate;

public class JSONTemplate {
	/*
	 * 创建新的NETCONF设备连接
	 */
	public static String SpawnDeviceJSON ="{\"node\":[{\"node-id\":\"\",\"host-tracker-service:id\":\"\","
			+ "\"netconf-node-topology:username\":\"\",\"netconf-node-topology:password\":\"\","
			+ "\"netconf-node-topology:host\":\"\",\"netconf-node-topology:port\":\"\","
			+ "\"netconf-node-topology:tcp-only\":\"\",\"netconf-node-topology:keepalive-delay\":\"0\","
			+ "\"netconf-node-topology:pass-through\":{}}]}";
	/*
	 * 在OLT下创建新的虚拟切片配置
	 */
	public static String SpawnNewVirtualJson ="{\"zxr10-pm-lr:"
	        + "virtual-network-device-config-vndx\":[{\"zxr10-pm-lr:vndx-name\":\"\","
	        + "\"zxr10-pm-lr:vndx-attribute\":{\"zxr10-pm-lr:description\":\"\","
	        + "\"zxr10-pm-lr:status\":\"\"},\"zxr10-pm-lr:deploy-mpu\":[],"
	        + "\"zxr10-pm-lr:assign-interface\":[]}]}";
	/*
	 * deploy-mpu资源
	 */
	public static String deployMpu ="{\"zxr10-pm-lr:mpu\":\"\"}";
	/*
	 * interface列表
	 */
	public static String AssignInterface = "{\"zxr10-pm-lr:interface-name\":\"\"}";
	/*
	 * vnd的attribute属性
	 */
	public static String Vnd_Attribute = "{\"zxr10-pm-lr:vndx-attribute\":{\"zxr10-pm-lr:status\":\"\"}}";
	/*
	 * 为VND添加端口资源
	 */
	public static String Vnd_addResources = "{\"zxr10-pm-lr:virtual-network-device-config-vndx\":"
	        + "[{\"zxr10-pm-lr:vndx-name\":\"\",\"zxr10-pm-lr:assign-interface\":[]}]}";
	/*
	 *  创建上联口vlan(tag或者untag)
	 */
	public static String createVlan = "{\"zxr10-vlan-dev-c600:if-vlan\":"
	        + "[{\"zxr10-vlan-dev-c600:mode\":\"\",\"zxr10-vlan-dev-c600:if-sub-index\":\"\",\""
	        + "zxr10-vlan-dev-c600:vlan-info\":\"\",\"zxr10-vlan-dev-c600:if-index\":\"\"}]}";
	/*
	 * 修改上联口VLAN
	 */
	public static String editVlan = "{\"zxr10-vlan-dev-c600:if-vlan\":"
	        + "{\"zxr10-vlan-dev-c600:if-vlan\":[{\"zxr10-vlan-dev-c600:mode\":\"\",\""
	        + "zxr10-vlan-dev-c600:if-sub-index\":\"\",\"zxr10-vlan-dev-c600:vlan-info\":\"\","
	        + "\"zxr10-vlan-dev-c600:if-index\":\"\"}]}}";
}
