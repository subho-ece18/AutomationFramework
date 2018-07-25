package util;

public class ReportThemeFactory {
	public static ReportTheme getReportsTheme(ReportThemeFactory.Theme theme) {
		ReportTheme reportTheme = new ReportTheme();
		switch ($SWITCH_TABLE$com$c$f$ReportThemeFactory$Theme()[theme
				.ordinal()]) {
		case 1:
			reportTheme.setHeadingBackColor("#495758");
			reportTheme.setHeadingForeColor("#95A3A4");
			reportTheme.setSectionBackColor("#8B9292");
			reportTheme.setSectionForeColor("#001429");
			reportTheme.setContentForeColor("#282A2A");
			reportTheme.setContentBackColor("#EDEEF0");
			break;
		case 2:
			reportTheme.setHeadingBackColor("#4D7C7B");
			reportTheme.setHeadingForeColor("#FFFF95");
			reportTheme.setSectionBackColor("#89B6B5");
			reportTheme.setSectionForeColor("#333300");
			reportTheme.setContentBackColor("#FAFAC5");
			reportTheme.setContentForeColor("#000000");
			break;
		case 3:
			reportTheme.setHeadingBackColor("#9933FF");
			reportTheme.setHeadingForeColor("#FFD1FF");
			reportTheme.setSectionBackColor("#F5E6E6");
			reportTheme.setSectionForeColor("#3D001F");
			reportTheme.setContentForeColor("#8F0047");
			reportTheme.setContentBackColor("#F6F3E4");
			break;
		case 4:
			reportTheme.setHeadingBackColor("#86816A");
			reportTheme.setHeadingForeColor("#333300");
			reportTheme.setSectionBackColor("#A6A390");
			reportTheme.setSectionForeColor("#001F00");
			reportTheme.setContentForeColor("#003326");
			reportTheme.setContentBackColor("#E8DEBA");
			break;
		case 5:
			reportTheme.setHeadingBackColor("#953735");
			reportTheme.setHeadingForeColor("#FFA3A3");
			reportTheme.setSectionBackColor("#CC9999");
			reportTheme.setSectionForeColor("#4D0000");
			reportTheme.setContentForeColor("#3D1F00");
			reportTheme.setContentBackColor("#D9D9D9");
			break;
		case 6:
			reportTheme.setHeadingBackColor("#CE824E");
			reportTheme.setHeadingForeColor("#291A10");
			reportTheme.setSectionBackColor("#AA9B7C");
			reportTheme.setSectionForeColor("#3D3D29");
			reportTheme.setContentForeColor("#996600");
			reportTheme.setContentBackColor("#F8F1E7");
			break;
		case 7:
			reportTheme.setHeadingBackColor("#CC8099");
			reportTheme.setHeadingForeColor("#470047");
			reportTheme.setSectionBackColor("#C285C2");
			reportTheme.setSectionForeColor("#3D001F");
			reportTheme.setContentForeColor("#330080");
			reportTheme.setContentBackColor("#C5AFC6");
		}

		return reportTheme;
	}

	private static int[] $SWITCH_TABLE$com$c$f$ReportThemeFactory$Theme() {
		// TODO Auto-generated method stub
		return null;
	}

	public static enum Theme {
		CLASSIC, MYSTIC, AUTUMN, OLIVE, REBEL, RETRO, SERENE;
	}
}

