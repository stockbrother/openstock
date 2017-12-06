package daydayup.openstock.wash;

import java.io.File;
import java.io.Reader;

import daydayup.openstock.wash.WashedFileLoader.WashedFileLoadContext;

public interface WashedFileProcessor {

	public void process(File file,Reader reader, WashedFileLoadContext xContext);
}