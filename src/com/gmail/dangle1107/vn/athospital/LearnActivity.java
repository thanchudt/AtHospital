package com.gmail.dangle1107.vn.athospital;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LearnActivity extends Activity{
	Button btnRemember;
	Button btnMiss;
	ImageView imageViewContent;	
	ImageView imageViewDifficultLevel;
	private KnowledgeDataSource knowledgeDataSource;
	private LearnDataSource learnDataSource;
	private int current_index;
	List<Knowledge> lstKnowledge;
	List<KnowledgeDto> lstKnowledgeDto;
	final static int MAX_NUMBER_IMAGE = 11;
	final static String MAX_IMAGE_NAME = "img00011.png";
	int realMaxNumberImage;
	int userLevel; 
	int userSubLevel;
	long totalMark = 0;
	final static int CURRENT_USER_ID = 1;
	final static int AMOUNT_OF_LEVEL = 4;
	final static int AMOUNT_OF_SUBLEVEL = 5;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn); 		
			
		knowledgeDataSource = new KnowledgeDataSource(this);
		knowledgeDataSource.open();
		lstKnowledge = knowledgeDataSource.getAllKnowledges();
		knowledgeDataSource.close();				
		InitKnowledge();		
		current_index = -1;
		imageViewContent = (ImageView) findViewById(R.id.imageViewContent);
		imageViewDifficultLevel = (ImageView) findViewById(R.id.imageViewDifficultLevel);
		DrawContent();				
	}
	
	private void setRealMaxNumberImage(){
		int maxNumberImage = Math.min(MAX_NUMBER_IMAGE, lstKnowledgeDto.size());
		long rememberedImage = totalMark / 10;
		int numberOfImageInALevel = maxNumberImage / AMOUNT_OF_LEVEL;
		int numberOfImageInASubLevel = maxNumberImage / AMOUNT_OF_LEVEL / AMOUNT_OF_SUBLEVEL;
		for(int i = 1; i <= AMOUNT_OF_LEVEL; i++){
			int level = numberOfImageInALevel * i;
			if(rememberedImage <= level){
				realMaxNumberImage = level;
				userLevel = i;
				userSubLevel = 1;
				for(int j = 1; j < AMOUNT_OF_SUBLEVEL; j++){
					int subLevel = level - numberOfImageInASubLevel * j;
					if(rememberedImage > subLevel){
						userSubLevel = AMOUNT_OF_SUBLEVEL - j + 1;
						break;
					}
				}
				break;
			}
		}		
	}
	
	private void setTextViewUserLevel()	{
		TextView t=new TextView(this); 
	    t=(TextView)findViewById(R.id.textViewUserLevel); 
	    String subLevelText = Integer.toString(userSubLevel);
	    switch(userLevel){
	    case 1:
	    	t.setText("Level: Beginner " + subLevelText);
	    	break;
	    case 2:
	    	t.setText("Level: Competent " + subLevelText);
	    	break;
	    case 3:
	    	t.setText("Level: Proficient " + subLevelText);
	    	break;
	    case 4:
	    	t.setText("Level: Expert " + subLevelText);
	    	break;	    
	    }
	    
	}
	private void InitKnowledge() {
		ToKnowledgeDtos();
		Collections.sort(lstKnowledgeDto, new MarkComparator());		
		setRealMaxNumberImage();
		setTextViewUserLevel();
	}
	
	private void ToKnowledgeDtos()
	{
		lstKnowledgeDto = new ArrayList<KnowledgeDto>();
		int maxRange = Math.min(MAX_NUMBER_IMAGE, lstKnowledge.size());
		int weight = 0;
		learnDataSource = new LearnDataSource(this);
		learnDataSource.open();			
		int count = 0;
		for(int i=0;i<lstKnowledge.size();i++)
		{
			if(count == maxRange)
				break;
			if(MAX_IMAGE_NAME.compareToIgnoreCase(lstKnowledge.get(i).getContent()) < 0)
				continue;
			count++;			
			Learn learn = learnDataSource.getLearn(CURRENT_USER_ID, lstKnowledge.get(i).getId());
			KnowledgeDto item = new KnowledgeDto();
			item.id = lstKnowledge.get(i).getId();
			item.content = lstKnowledge.get(i).getContent();
			item.category_id = lstKnowledge.get(i).getCategoryId();
			item.subject_id = lstKnowledge.get(i).getSubjectId();
			item.mark = -1;
			item.times = 0;
			item.totalMark = 0;
			if(learn != null)				
			{				
				if(learn.getTimes() != 0)
				{
					item.times = learn.getTimes();
					item.mark = learn.getTotalMark() / learn.getTimes();
					item.totalMark = learn.getTotalMark();
					item.order =item.mark; 
					totalMark += item.mark;
				}
			}
			if(item.mark == -1)
				item.order = -1 + weight++;		
			lstKnowledgeDto.add(item);
		}
		learnDataSource.close();
	}
	private void DrawContent() {				
		setCurrentIndex();
		if(current_index < 0)
			return;
		String path= "@drawable/" + lstKnowledgeDto.get(current_index).content;
		int imageResource = getResources().getIdentifier(path, null, getPackageName());
		Drawable res = getResources().getDrawable(imageResource);		
		imageViewContent.setImageDrawable(res);
		long mark = lstKnowledgeDto.get(current_index).mark;
		if(mark > 9)
			imageViewDifficultLevel.setImageResource(R.drawable.zero_star);
		else if(mark > 8)
			imageViewDifficultLevel.setImageResource(R.drawable.one_star);
		else if(mark > 6)
			imageViewDifficultLevel.setImageResource(R.drawable.two_star);
		else if(mark > 4)
			imageViewDifficultLevel.setImageResource(R.drawable.three_star);
		else if(mark > 2)
			imageViewDifficultLevel.setImageResource(R.drawable.four_star);
		else 
			imageViewDifficultLevel.setImageResource(R.drawable.five_star);			
	}
	
	private void setCurrentIndex()
	{
		current_index++;
		if(current_index > realMaxNumberImage - 1)
		{
			InitKnowledge();
			current_index=0;
		}
	}
	/*private void DrawContent() {
		Utility utility = new Utility();
		//current_index = utility.mod((current_index+1),values.size());
		getCurrentIndex();		 
		String path= "@drawable/" + lstKnowledge.get(current_index).getContent();
		int imageResource = getResources().getIdentifier(path, null, getPackageName());
		Drawable res = getResources().getDrawable(imageResource);		
		imageViewContent.setImageDrawable(res);
		
		Learn learn = learnDataSource.getLearn(CURRENT_USER_ID, lstKnowledge.get(current_index).getId());
		
		if(learn == null)
		{
			imageViewDifficultLevel.setImageResource(R.drawable.zero_star);			
		}else
		{
			long hard = 10;
		
			if(learn.getTimes() != 0) 
				hard = learn.getTotalMark() / learn.getTimes();
			if(hard > 9)
				imageViewDifficultLevel.setImageResource(R.drawable.zero_star);
			else if(hard > 8)
				imageViewDifficultLevel.setImageResource(R.drawable.one_star);
			else if(hard > 6)
				imageViewDifficultLevel.setImageResource(R.drawable.two_star);
			else if(hard > 4)
				imageViewDifficultLevel.setImageResource(R.drawable.three_star);
			else if(hard > 2)
				imageViewDifficultLevel.setImageResource(R.drawable.four_star);
			else 
				imageViewDifficultLevel.setImageResource(R.drawable.five_star);
		}
	}*/
	

	/*private void getCurrentIndex() {
		Random r = new Random();
		int minRange = 0;
		int maxRange = Math.min(MAX_NUMBER_IMAGE, lstKnowledge.size()) - 1;
		current_index = r.nextInt(maxRange - minRange + 1) + minRange;
	}*/
	
	public void remember(View view){		
		KnowledgeDto item = lstKnowledgeDto.get(current_index);
		item.times++;		
		totalMark = totalMark - item.mark + (10 + item.totalMark) / item.times;
		item.mark = (10 + item.totalMark) / item.times;
		setRealMaxNumberImage();
		setTextViewUserLevel();
		Date currentDate = new Date(System.currentTimeMillis());
		learnDataSource = new LearnDataSource(this);
		learnDataSource.open();		
		learnDataSource.createLearn(CURRENT_USER_ID, lstKnowledgeDto.get(current_index).id, currentDate, 10);
		learnDataSource.close();
		DrawContent();
	}
	
	public void miss(View view){
		KnowledgeDto item = lstKnowledgeDto.get(current_index);
		item.times++;		
		totalMark = totalMark - item.mark + item.totalMark / item.times;
		item.mark = item.totalMark / item.times;		
		setRealMaxNumberImage();
		setTextViewUserLevel();
		Date currentDate = new Date(System.currentTimeMillis());
		learnDataSource = new LearnDataSource(this);
		learnDataSource.open();	
		learnDataSource.createLearn(CURRENT_USER_ID, lstKnowledgeDto.get(current_index).id, currentDate, 0);
		learnDataSource.close();
		DrawContent();
	}
	
	@Override
	protected void onResume() {		
		super.onResume();
		
	}

	@Override
	protected void onPause() {
		super.onPause();		
	}
}
