var ajaxUrl = "ajax/admin/users/";
var datatableApi;

function setup() {
    $("#user-enable").change(function () {
        $.ajax({
            url: ajaxUrl + $(this).parent().parent().attr("id") + "/" + $(this).is(':checked'),
            type: 'PUT',
            success: function (data) {
                alert('User was updated');
            }
        });
    });
}

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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

    setup()
});