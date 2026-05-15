package view;

import model.dto.response.UserResponseDto;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;
import util.APIResponseTemplate;

import java.util.List;

public class UserTableView {

    private static final CellStyle CENTER = new CellStyle(CellStyle.HorizontalAlign.center);
    private static final CellStyle LEFT   = new CellStyle(CellStyle.HorizontalAlign.left);

    private static final String[] HEADERS = {"UUID", "Name", "Email Address", "Profile URL"};

    public static void display(APIResponseTemplate<List<UserResponseDto>> response) {

        System.out.println("Status : " + response.status());
        System.out.println("Message: " + response.message());
        System.out.println("Date   : " + response.timeStamp());
        System.out.println();

        List<UserResponseDto> users = response.data();

        Table table = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER);

        for (String col : HEADERS) {
            table.addCell(col, CENTER);
        }

        table.setColumnWidth(0, 36, 38);
        table.setColumnWidth(1, 15, 25);
        table.setColumnWidth(2, 25, 40);
        table.setColumnWidth(3, 30, 60);

        for (UserResponseDto u : users) {
            table.addCell(u.uuid(), CENTER);
            table.addCell(u.name(), LEFT);
            table.addCell(u.email(), LEFT);
            table.addCell(u.profile(), LEFT);
        }

        System.out.println(table.render());
    }

    public static void menu() {
        Table menu = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER);
        menu.setColumnWidth(0, 30, 30);

        menu.addCell("USER MANAGEMENT SYSTEM", CENTER);
        menu.addCell("1. Create New User", CENTER);
        menu.addCell("2. List All Users", CENTER);
        menu.addCell("3. Delete User by UUID", CENTER);
        menu.addCell("4. Update User info", CENTER);
        menu.addCell("5. Search by Name", CENTER);
        menu.addCell("6. Search by UUID", CENTER);
        menu.addCell("0. Exit System", CENTER);

        System.out.println(menu.render());
    }
}