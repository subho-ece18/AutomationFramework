package util;
import util.FrameworkException.*;

@SuppressWarnings("unused")
public class ExcelCellFormatting {
	private String fontName;
	private short fontSize;
	private short backColorIndex;
	private short foreColorIndex;
	public boolean bold = false;
	public boolean italics = false;
	public boolean centred = false;

	public String getFontName() {
		return this.fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public short getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(short fontSize) {
		this.fontSize = fontSize;
	}

	public short getBackColorIndex() {
		return this.backColorIndex;
	}

	public void setBackColorIndex(int i) {
		if (i >= 8 && i <= 64) {
			this.backColorIndex = (short) i;
		} else {
			//throw new FrameworkException(
					//"Valid indexes for the Excel custom palette are from 0x8 to 0x40 (inclusive)!");
		}
	}

	public short getForeColorIndex() {
		return this.foreColorIndex;
	}

	public void setForeColorIndex(int i) {
		if (i >= 8 && i <= 64) {
			this.foreColorIndex = (short) i;
		} else {
			//throw new Exception(
//					"Valid indexes for the Excel custom palette are from 0x8 to 0x40 (inclusive)!");
		}
	}
}
