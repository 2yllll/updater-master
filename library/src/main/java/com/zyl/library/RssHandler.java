package com.zyl.library;


import com.zyl.library.objects.UpdateInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.net.MalformedURLException;
import java.net.URL;

class RssHandler extends DefaultHandler {
	private UpdateInfo update;
	private StringBuilder builder;

	public UpdateInfo getUpdate() {
		return update;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);

		if (this.update != null) {
			builder.append(ch, start, length);
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);

		if (this.update != null) {
			if (localName.equals("serverAppVersion")) {
				update.setServerAppVersion(builder.toString().trim());
			} else if (localName.equals("releaseMemo")) {
				update.setReleaseMemo(builder.toString().trim());
			} else if (localName.equals("url")) {
				try {
					update.setDownloadUrl(new URL(builder.toString().trim()));
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				}
			}

			builder.setLength(0);
		}
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();

		builder = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName,
							 String name, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		if (localName.equals("update")) {
			update = new UpdateInfo();
		}
	}

}
