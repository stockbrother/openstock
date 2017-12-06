package daydayup.openstock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import daydayup.openstock.cninfo.CninfoCorpInfo2DbSheetCommand;
import daydayup.openstock.database.DataBaseService;
import daydayup.openstock.document.Spreadsheet;
import daydayup.openstock.document.SpreadsheetDocument;
import daydayup.openstock.sheetcommand.FillIndexSheetCommand;
import daydayup.openstock.sheetcommand.IndexTableSheetCommand;
import daydayup.openstock.sheetcommand.SqlQuerySheetCommand;
import daydayup.openstock.sheetcommand.SqlUpdateSheetCommand;
import daydayup.openstock.sheetcommand.Washed2DbSheetCommand;
import daydayup.openstock.sina.SinaQuotesDownloadAndWashSheetCommand;
import daydayup.openstock.sina.SinaQuotesWashed2DBSheetCommand;
import daydayup.openstock.sse.SseCorpInfo2DbSheetCommand;
import daydayup.openstock.sse.SseCorpInfoFullNameAndCategory2DbSheetCommand;
import daydayup.openstock.szse.SzseCorpInfo2DbSheetCommand;

public class SheetCommand extends CommandBase<Object> {

	private static final Logger LOG = LoggerFactory.getLogger(SheetCommand.class);

	public static final String SN_SYS_CMDS = "SYS_CMDS";

	public static final String SN_SQL_QUERY = "SQL_QUERY";

	public static final String SN_SQL_UPDATE = "SQL_UPDATE";

	public static final String SN_SYS_INDEX_DEFINE = "SYS_INDEX_DEFINE";

	public static final String SN_SYS_SCOPED_INDEX_TABLE = "SYS_SCOPED_INDEX_TABLE";

	public static final String SN_SYS_INDEX_TABLE = "INDEX_TABLE";
	
	public static final String CMD_FILL_INDEX = "FILL_INDEX";
	
	public static final String CMD_WASHED_2_DB = "WASHED_2_DB";

	public static final String SN_SYS_CFG = "SYS_CFG";

	@Override
	public Object doExecute(CommandContext cc) {
		
		DataBaseService dbs = cc.getDataBaseService();
		Spreadsheet xSheet = cc.getActiveSpreadsheet();
		String command = xSheet.getText(0, 0);
		if(!"Command".equals(command)){
			LOG.warn("not a command sheet.");
			return "not a command sheet";
		}
		command = xSheet.getText(1, 0);
		
		if (command == null) {
			LOG.warn("no command found.");
			return "no command found.";
		}

		SheetCommandContext scc = new SheetCommandContext(cc,xSheet, command);

		if (command.equals(SN_SQL_QUERY)) {
			return new SqlQuerySheetCommand().execute(scc);
		} else if (command.equals(SN_SQL_UPDATE)) {
			return new SqlUpdateSheetCommand().execute(scc);
		} else if (command.equals(CMD_FILL_INDEX)) {
			return new FillIndexSheetCommand().execute(scc);
		} else if (command.equals(SN_SYS_INDEX_TABLE)) {
			return new IndexTableSheetCommand().execute(scc);
		} else if (command.equals(CMD_WASHED_2_DB)) {
			return new Washed2DbSheetCommand().execute(scc);		
		} else if (command.equals("CNINFO_CORPINFO_2_DB")) {
			return new CninfoCorpInfo2DbSheetCommand().execute(scc);
		} else if (command.equals("SINA_DOWNLOAD_AND_WASH")) {
			return new SinaQuotesDownloadAndWashSheetCommand().execute(scc);
		} else if (command.equals("SINA_WASHED_2_DB")) {
			return new SinaQuotesWashed2DBSheetCommand().execute(scc);
		} else if (command.equals("SSE_CORPINFO_2_DB")) {
			return new SseCorpInfo2DbSheetCommand().execute(scc);
		} else if (command.equals("SSE_CORPINFO_FULLNAME_CATEGORY_2_DB")) {
			return new SseCorpInfoFullNameAndCategory2DbSheetCommand().execute(scc);
		} else if (command.equals("SZSE_CORPINFO_2_DB")) {
			return new SzseCorpInfo2DbSheetCommand().execute(scc);
		}else {
			return "not supporte:" + command;
		}
	}

	private Object executeResetSheet(CommandContext cc) {
		SpreadsheetDocument xDoc = cc.getDocument();

		String[] names = xDoc.getSheetNames();
		for (String name : names) {
			if (name.startsWith("SYS_")) {
				continue;
			}
			
			xDoc.removeByName(name);
			
		}
		return "done";
	}

}
