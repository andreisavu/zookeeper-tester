<#-- @ftlvariable name="" type="ro.pub.master.sii.zookeeper.views.HomeView" -->
<!DOCTYPE html>
<html lang="en">

<head>
    <link rel="stylesheet" href="/assets/css/bootstrap.css"/>
    <link rel="stylesheet" href="/assets/css/bootstrap-responsive.css"/>

    <script type="text/javascript" src="/assets/js/jquery.min.js"></script>
    <script type="text/javascript" src="/assets/js/bootstrap.js"></script>
    <script type="text/javascript" src="/assets/js/highcharts.js"></script>
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

    <br />
    <h3>metrics</h3>

</div>
</body>

</html>