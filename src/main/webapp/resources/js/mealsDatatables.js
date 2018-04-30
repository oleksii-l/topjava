var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function setupFilter() {
    $("#meal-filter").click(function () {
            var form = $("#filter-form");
            var url = ajaxUrl + "filter?" + form.serialize();
            updateTable(url);
        }
    );

    $("#filter-reset").click(function () {
        $("#filter-form")[0].reset();
    });
}

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
    setupFilter()
});