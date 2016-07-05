   $(function () {
   for(int i=0; i<3; i++) {
    $('#pie'+i).highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Browser market shares January, 2015 to May, 2015'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            name: 'Brands',
            colorByPoint: true,
            data: [{
                name: 'AIR-CT5508-K9',
                y: 56.33
            }, {
                name: 'AIR-CT5508-J9',
                y: 24.03,
                sliced: true,
                selected: true
            }, {
                name: 'AIR-CT5578-K9',
                y: 10.38
            }, {
                name: 'ABC-CT5508-K9',
                y: 4.77
            }, {
                name: 'XYZ-CT5508-K9',
                y: 0.91
            }, {
                name: 'Others',
                y: 0.2
            }]
        }]
    });
}
});
