+$(function () {
    
    // 取得ContextPath    
    function getContextPath() {
        var sPathName = document.location.pathname;
        var iIndex = sPathName.substr(1).indexOf("/");
        var sResult = sPathName.substr(0, iIndex+1);
        return sResult;
    }
    var username = $("#username").content();
    var sContextPath = getContextPath();
    var directionInput = document.getElementById("direction"); 
    var network = null;
    var id = 1;
    //如果是虚拟切片 name可以设置为控制器
//    var olt = {
//            "name": "olt1",
//            "volt":[
//                    {
//                    "id": "volt1",
//                    "slot": [
//                    {
//                        "id": "slot1",
//                        "pon": [
//                            {
//                              "id": "pon1",
//                              "onu": [
//                                    {
//                                        "id": "onu1"
//                                    },
//                                    {
//                                        "id": "onu2"
//                                    }
//                                 ]                    
//                            },
//                            {
//                                "id": "pon2",
//                                "onu": [
//                                    {
//                                        "id": "onu1"
//                                    }
//                                ]
//                            }
//                        ]
//                    },
//                    {
//                        "id": "slot2",
//                        "pon": [
//                            {
//                                "id": "pon1",
//                                "onu": [
//                                    {
//                                        "id": "onu1"
//                                    },
//                                    {
//                                        "id": "onu2"
//                                    }
//                                ]
//                            },
//                            {
//                                "id": "pon2",
//                                "onu": [
//                                    {
//                                        "id": "onu1"
//                                    }
//                                ]
//                            }
//                        ]
//                    }
//                ]
//            }
//            ]
//        }
    //获取拓扑数据
    function getTopoData(){
        var oAjaxOption = {
                type: "get",
                url: sContextPath + "/rest/getTopoInfo/"+username+".json",
                contentType: "application/json",
                dataType: "text",
                success: function(oData, oStatus) {
                    var a = JSON.parse(oData);
                    for( var number in a ){
                        draw(a[number]);
                    }
                },
                error: function(oData, oStatus, eErrorThrow) {
                    util.handleAjaxError(oData, oStatus, eErrorThrow);
                },
                complete: function (oXmlHttpRequest, oStatus) {
                    $.unblockUI();
                }
        };
        $.blockUI(util.getBlockOption());
        $.ajax(oAjaxOption);
    }
    //建立拓扑图
    function setOLTtopo(olt1){
        var oltId = id-1;
        if(username == "admin"){
            nodes.push({id: oltId, label: "olt:"+olt1.name});
        }else{
            nodes.push({id: oltId, label: username});
        }
        nodes[oltId]["level"] = 0;
        if(olt1.volt != null){
            for (var i=0; i<olt1.volt.length;i++) {
                nodes.push({id: id, label: "切片:"+olt1.volt[i].id});//, color: 'lime'
                edges.push({from: 0, to:id});
                nodes[id]["level"] = 1;
                var voltId = id;
                id++
                if(olt1.volt[i].slot != null){
                    for(var j=0; j<olt1.volt[i].slot.length;j++){
                        nodes.push({id: id, label: olt1.volt[i].slot[j].id});
                        edges.push({from: voltId, to:id}); 
                        nodes[id]["level"] = 2; 
                        var slotId = id;
                        id++;
                        if(olt1.volt[i].slot[j].pon != null){
                            for(var k=0;k<olt1.volt[i].slot[j].pon.length;k++){
                                nodes.push({id: id, label: olt1.volt[i].slot[j].pon[k].id});
                                nodes[id]["level"] = 3;
                                edges.push({from: slotId, to:id});
                                var ponId = id;
                                id++;
                                if(olt1.volt[i].slot[j].pon[k].onu != null){
                                    for(var m=0;m<olt1.volt[i].slot[j].pon[k].onu.length;m++){
                                        nodes.push({id: id, label: olt1.volt[i].slot[j].pon[k].onu[m].id});
                                        nodes[id]["level"] = 4;
                                        edges.push({from: ponId, to:id});
                                        id++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            id++;
        }
        
    }
       function destroy() {
        if (network !== null) {
            network.destroy();
            network = null;
        }
    }

    function draw(olt) {
        destroy();
        nodes = [];
        edges = [];
        var connectionCount = [];
        setOLTtopo(olt);
        console.log(nodes);



        // create a network
        var container = document.getElementById('mynetwork');
        var data = {
            nodes: nodes,
            edges: edges
        };

//        var options = {
//            edges: {
//                smooth: {
//                    type: 'cubicBezier',
//                    forceDirection: (directionInput.value == "UD" || directionInput.value == "DU") ? 'vertical' : 'horizontal',
//                    roundness: 0.4
//                }
//            },
//            layout: {
//                hierarchical: {
//                    direction: directionInput.value
//                }
//            },
//            physics:false
//        };
        var options = {
                layout: {
                    hierarchical: {
                        direction: directionInput
                    }
                },
                nodes: {
                    shape: 'box',
                    margin: 10,
                    widthConstraint: {
                      maximum: 200
                    }
                  },
                physics: {
                    enabled:false
                  },
            };
        network = new vis.Network(container, data, options);

        // add event listeners
        network.on('select', function (params) {
            document.getElementById('selection').innerHTML = 'Selection: ' + params.nodes;
        });
    }
    //初始化
    getTopoData();
//    draw(olt);
});