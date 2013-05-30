package de.dailab.plistacontest.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Desirializer {

	private static Logger logger = LoggerFactory.getLogger(Desirializer.class);

	@SuppressWarnings("unchecked")
	public static <T> T deserialize(final String _file) {
		T tmp = null;
		try {
			final FileInputStream fileIn = new FileInputStream(_file);
			final ObjectInputStream in = new ObjectInputStream(fileIn);
			tmp = (T) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (ClassNotFoundException e1) {
			logger.error(e1.getMessage());
		}

		return tmp;
	}

}
