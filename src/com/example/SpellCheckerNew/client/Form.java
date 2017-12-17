package com.example.SpellCheckerNew.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import com.example.SpellCheckerNew.shared.EmptyFormatException;
import com.example.SpellCheckerNew.shared.UnknownWordsException;
import com.example.SpellCheckerNew.shared.Word;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Form extends Composite implements HasText {

	private static FormUiBinder uiBinder = GWT.create(FormUiBinder.class);

	interface FormUiBinder extends UiBinder<Widget, Form> {
	}

	@UiField
	Button reset;
	@UiField
	Button check;
	@UiField
	Button readme;
	@UiField
	RichTextArea text;
	@UiField
	FlowPanel correctionField;
	@UiField
	FlowPanel applyField;

	public Form() {

		initWidget(uiBinder.createAndBindUi(this));

	}

	public Form(String firstName) {

		initWidget(uiBinder.createAndBindUi(this));

	}

	@UiHandler("check")
	void onCheck(ClickEvent e) {
		submitForm();
	}

	@UiHandler("reset")
	void onReset(ClickEvent e) {
		RootPanel.get().clear();
		RootPanel.get().add(new Form());
	}

	/**
	 * On click event show Information.
	 * 
	 * @param e
	 */
	@UiHandler("readme")
	void onRead(ClickEvent e) {
		correctionField.clear();
		applyField.clear();
		HTML html = new HTML(getInformationAboutTheSpellChecker());
		correctionField.add(html);
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub

	}

	/**
	 * Method used for submitForm.
	 */
	public void submitForm() {
		correctionField.clear();
		applyField.clear();
		GreetingServiceAsync async = GWT.create(GreetingService.class);
		async.greetServer(text.getText(), new AsyncCallback<HashMap<Word, HashSet<Word>>>() {
			/**
			 * On Success Highlight misspelled words.
			 * 
			 * @param result
			 */
			@Override
			public void onSuccess(final HashMap<Word, HashSet<Word>> result) {
				// TODO Auto-generated method stub
				String userInput = text.getText().toLowerCase();

				final String[] tokens = userInput.split("\\W+");
				for (int i = 0; i < tokens.length; i++) {
					for (Word w : result.keySet()) {

						// marks the word that is misspelled
						if (tokens[i].equals(w.getContent())) {
							tokens[i] = "<b style=\"background-color: #daa520\">" + w.getContent() + "</b>";

							// Creates ListBox with the suggestions for the
							// misspelled word.
							ListBox listBox = new ListBox();
							for (Word corr : result.get(w)) {
								listBox.addItem(corr.getContent());
							}
							listBox.addItem(w.getContent());
							listBox.setVisibleItemCount(11);
							correctionField.add(listBox);
						}
					}
				}

				StringBuilder sB = new StringBuilder();
				for (String s : tokens) {
					sB.append(s);
					sB.append(" ");
				}
				String content = sB.toString().trim();
				text.setHTML(content + "&nbsp;");

				// Button to apply the changes to the RichTextArea
				Button apply = new Button("Apply");
				if (result.keySet().isEmpty()) {

					correctionField.clear();
					applyField.clear();
					HTML html = new HTML(noMisspelledWords());
					correctionField.add(html);

				} else {
					applyField.add(apply);
				}
				apply.addClickHandler(new ClickHandler() {
					/**
					 * On click edit List box.
					 * 
					 * @param event
					 *            OnClick Event
					 */
					@Override
					public void onClick(ClickEvent event) {

						// TODO Auto-generated method stub
						int numOfWidgets = correctionField.getWidgetCount();
						for (int i = 0; i < numOfWidgets; i++) {
							ListBox tmp = (ListBox) correctionField.getWidget(i);
							String listBoxItem = tmp.getSelectedItemText();
							Word listBoxItemWord = new Word(listBoxItem);
							for (int k = 0; k < tokens.length; k++) {
								tokens[k] = tokens[k].replace("<b style=\"background-color: #daa520\">", "");
								tokens[k] = tokens[k].replace("</b>", "");
								tokens[k] = tokens[k].trim();

								for (Word w : result.keySet()) {

									if (tokens[k].equals(w.getContent()) && result.get(w).contains(listBoxItemWord)) {
										tokens[k] = listBoxItem.trim();
									}

								}
							}

							StringBuilder sb = new StringBuilder();
							for (int j = 0; j < tokens.length; j++) {
								sb.append(tokens[j]);
								sb.append(" ");
							}
							text.setText(sb.toString().trim());
						}

					}
				});

			}

			/**
			 * When failed show error.
			 * 
			 * @param caught
			 *            caught exception
			 */
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				if (caught instanceof UnknownWordsException) {
					correctionField.clear();
					applyField.clear();
					HTML html = new HTML(toMuchMisspelledWords());
					correctionField.add(html);
				} else if (caught instanceof EmptyFormatException) {
					correctionField.clear();
					applyField.clear();
					HTML html = new HTML(emptyTextArea());
					correctionField.add(html);
				} else if (caught instanceof IOException) {
					correctionField.clear();
					applyField.clear();
					HTML html = new HTML(dictNotFound());
					correctionField.add(html);
				} else if (caught instanceof IllegalArgumentException) {
					correctionField.clear();
					applyField.clear();
					HTML html = new HTML(illegalArgumentException());
					correctionField.add(html);
				} else {
					correctionField.clear();
					applyField.clear();
					HTML html = new HTML(unknownException());
					correctionField.add(html);
				}

			}
		});
	}

	/**
	 * Spellchecker Information
	 * 
	 * @return return the Spellchecker Information.
	 */

	public String getInformationAboutTheSpellChecker() {
		return "<p style=\"color: black\"> welcome to MakeYourTeacherHappyDotCom.<br/> this project was programmed by students of the university of tuebingen.<br/>"
				+ "this application will correct any misspelled word for you. if there are too many mistakes, the input will not be processed."
				+ "<br/>in order to keep the accuracy as high as possible, all characters typed in will be converted to lower case characters and all non-word characters will be removed during the processing. "
				+ "<br/>after processing your entered text you will get a list of 10 possible corrections for every word. <br/>this list is based on the levenshtein algorythm. "
				+ "<br/>be aware that the last word in the suggestions list is the original word itself which was typed in, if you want to keep it."
				+ "<br/><br/>enjoy!</p>";
	}

	/**
	 * Text is empty
	 * 
	 * @return Empty message
	 */
	public String emptyTextArea() {
		return "<p style=\"color: black\">don't fool me. enter something.</p>";
	}

	/**
	 * Text has too many misspelled words
	 * 
	 * @return too many misspelled words message
	 */
	public String toMuchMisspelledWords() {
		return "<p style=\"color: black\">i can't take your input seriously. <br/>please enter some (more or less) human readable text. </p>";
	}

	/**
	 * Text does not contain misspelled words
	 * @return no misspelled words message
	 */
	public String noMisspelledWords() {
		return "<p style=\"color: black\">no misspelled words found.</p>";
	}
	
	/**
	 * Problem loading or finding the Dictionary
	 * @return problem finding the dict message
	 */
	public String dictNotFound(){
		return "<p style=\"color: black\">there was some problem finding the dictionary.</p>";
	}
	
	/**
	 * illegalArgumentException
	 * @return illegalArgumentException message
	 */
	public String illegalArgumentException(){
		return "<p style=\"color: black\">some error occured. please try it later.</p>";
	}
	
	/**
	 * if some unknown problem / exception occurs
	 * @return unknwon problem message
	 */
	public String unknownException(){
		return "<p style=\"color: black\">some unknown error occured, we are working hard to improve your experience.</p>";
	}

}
