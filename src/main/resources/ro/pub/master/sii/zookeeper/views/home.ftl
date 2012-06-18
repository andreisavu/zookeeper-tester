<#-- @ftlvariable name="" type="ro.pub.master.sii.zookeeper.views.HomeView" -->
<!DOCTYPE html>
<html lang="en">

<head>
    <link rel="stylesheet" href="/assets/css/bootstrap.css"/>
    <link rel="stylesheet" href="/assets/css/bootstrap-responsive.css"/>

    <script type="text/javascript" src="/assets/js/jquery.min.js"></script>
    <script type="text/javascript" src="/assets/js/bootstrap.js"></script>
    <script type="text/javascript" src="/assets/js/highcharts.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            Highcharts.setOptions({
                global:{
                    useUTC:false
                }
            });

            function newChart(renderTo, title, axisName, initFn, loadFn) {
                return new Highcharts.Chart({
                    chart:{
                        renderTo:renderTo,
                        type:'spline',
                        marginRight:10,
                        events:{
                            load:loadFn
                        }
                    },
                    title:{
                        text:title
                    },
                    xAxis:{
                        type:'datetime',
                        tickPixelInterval:150
                    },
                    yAxis:{
                        title:{
                            text:axisName
                        },
                        plotLines:[
                            {
                                value:0,
                                width:1,
                                color:'#808080'
                            }
                        ]
                    },
                    tooltip:{
                        formatter:function () {
                            return '<b>' + this.series.name + '</b><br/>' +
                                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                                    Highcharts.numberFormat(this.y, 2);
                        }
                    },
                    legend:{
                        enabled:false
                    },
                    exporting:{
                        enabled:false
                    },
                    series:[
                        {
                            name:'Latency',
                            data:initFn()
                        }
                    ]
                });
            }

            function noDataFor60Seconds() {
                var data = [],
                        time = (new Date()).getTime(),
                        i;

                for (i = -60; i <= 0; i++) {
                    data.push({
                        x:time + i * 1000,
                        y:0
                    });
                }
                return data;
            }

            var latencyChart = newChart("latencyChart", "End to End Queue Latency", "Latency",
                    noDataFor60Seconds,
                    function () {
                        // set up the updating of the chart each second
                        var series = this.series[0];
                        setInterval(function () {
                            $.get("/metrics/latency", function (data, status) {
                                series.addPoint([(new Date()).getTime(), data], true, true);
                            }, "json");
                        }, 1000);
                    }
            );
        });

        function toggleInjectorButtons(disabledOrNot) {
            $("#enableInjector").attr("disabled", disabledOrNot);
            $("#disableInjector").attr("disabled", disabledOrNot);
        }

        function enableInjector() {
            toggleInjectorButtons(true);
            $.post('/injector/enable', function (data, textStatus) {
                toggleInjectorButtons(false);
            });
        }

        function disableInjector() {
            toggleInjectorButtons(true);
            $.post('/injector/disable', function (data, textStatus) {
                toggleInjectorButtons(false);
            });
        }

    </script>

</head>

<body>
<div class="container" style="width: 960px;">
    <br/><br/>

    <h2># a tool for interactive fault injection testing
        (by <a href="http://twitter.com/andreisavu">@andreisavu</a>)
    </h2>
    <br/><br/>


    <h3>cluster nodes</h3>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Public IP</th>
            <th>Hostname</th>
        </tr>
        </thead>
        <tbody>
        <#list nodes as node>
        <tr>
            <td>${node.id}</td>
            <td><a target="_blank" href="${node.monitoringData}">${node.publicIp}</a></td>
            <td>${node.hostname}</td>
        </tr>
        </#list>
        </tbody>
    </table>

    <h3>injector control</h3>
    <br/>
    <button id="enableInjector" class="btn btn-danger" onclick="enableInjector()">
        Inject 10% Network Failures
    </button>
    <button id="disableInjector" class="btn btn-success" onclick="disableInjector()">
        Remove Injector
    </button>

    <br/><br/>

    <h3>workload generator metrics (queue)</h3>

    <div id="latencyChart" style="height: 300px;"></div>
    <br/><br/>

</div>
</body>

</html>