package com.imguang.demo.search;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.ansj.lucene5.AnsjAnalyzer;
import org.ansj.lucene5.AnsjAnalyzer.TYPE;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apdplat.word.lucene.ChineseWordAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.imguang.demo.neo4j.entity.Disease;
import com.imguang.demo.neo4j.entity.Medicine;
import com.imguang.demo.neo4j.entity.Symptom;
import com.imguang.demo.neo4j.service.DiseaseService;
import com.imguang.demo.neo4j.service.MedicineService;
import com.imguang.demo.neo4j.service.SymptomService;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

/**
 * @author 书生 Lucene 生成索引
 */
@Component
public class CreateIndex {

	private static Logger logger = LoggerFactory.getLogger(CreateIndex.class);
	private static Analyzer analyzer = null;
	private static IndexWriterConfig indexWriterConfig = null;
	private static Directory directory = null;
	private static IndexWriter indexWriter = null;

	@Autowired
	private DiseaseService diseaseService;

	@Autowired
	private MedicineService medicineService;

	@Autowired
	private SymptomService symptomService;

	public CreateIndex() throws IOException {
		File indexFile = new File(LuceneConst.INDEX_DIR);
		if (!indexFile.exists()) {
			indexFile.mkdirs();
		}
		directory = FSDirectory.open(indexFile.toPath());
//		analyzer = new AnsjAnalyzer(TYPE.index_ansj);
//		analyzer = new StandardAnalyzer();
		analyzer = new ChineseWordAnalyzer();
		indexWriterConfig = new IndexWriterConfig(analyzer);
		indexWriter = new IndexWriter(directory, indexWriterConfig);
	}

	public void addAll() throws IOException{
		addDisease();
		addMedicine();
		addSymptom();
		indexWriter.close();
	}
	
	/**
	 * 分页加入疾病实体
	 * @throws IOException
	 */
	private void addDisease() throws IOException{
		Pageable pageable = new PageRequest(0, 100, Sort.Direction.ASC, "id");
		Page<Disease> rePage = diseaseService.getByPage(pageable);
		while(true){
			addDisease(rePage.getContent());
			if(!rePage.hasNext()){
				break;
			}
			pageable = pageable.next();
			rePage = diseaseService.getByPage(pageable);
		}
	}
	
	/**
	 * 加入疾病索引
	 * 
	 * @param diseases
	 * @throws IOException
	 */
	private void addDisease(List<Disease> diseases) throws IOException {
		
		logger.info("添加" + diseases.size() + "条");
		int cnt = 0;
		for (Disease disease : diseases) {
			cnt++;
			logger.info("正在添加第" + cnt + "条。");
			// logger.info("treatement_detail:" + disease.getTreatmentDetail());
			Document document = new Document();
			document.add(new TextField("name", disease.getDiseaseName(), Store.YES));
			document.add(new TextField("id", disease.getId(), Store.YES));
			if (disease.getAbstractContent() != null && !"".equals(disease.getAbstractContent()))
				document.add(new TextField("abstract_content", disease.getAbstractContent(), Store.NO));
			if (disease.getEtiology() != null && !"".equals(disease.getEtiology()))
				document.add(new TextField("etiology", disease.getEtiology(), Store.NO));
			if (disease.getTreatmentDetail() != null && !"".equals(disease.getTreatmentDetail()))
				document.add(new TextField("treatment_detail", disease.getTreatmentDetail(), Store.NO));
			if (disease.getPrevent() != null && !"".equals(disease.getPrevent()))
				document.add(new TextField("prevent", disease.getPrevent(), Store.NO));
			if (disease.getNursing() != null && !"".equals(disease.getNursing()))
				document.add(new TextField("nursing", disease.getNursing(), Store.NO));
			document.add(new TextField("flag", "disease", Store.YES));
			indexWriter.addDocument(document);
		}
		logger.info("添加成功");
	}
	
	/**
	 * 分页加入药物索引
	 * @throws IOException
	 */
	private void addMedicine() throws IOException{
		Pageable pageable = new PageRequest(0, 100, Sort.Direction.ASC, "id");
		Page<Medicine> rePage = medicineService.getByPage(pageable);
		while(true){
			addMedicine(rePage.getContent());
			if(!rePage.hasNext()){
				break;
			}
			pageable = pageable.next();
			rePage = medicineService.getByPage(pageable);
		}
	}

	/**
	 * 加入药物索引
	 * 
	 * @param medicines
	 * @throws IOException
	 */
	private void addMedicine(List<Medicine> medicines) throws IOException {
		logger.info("添加" + medicines.size() + "条");
		int cnt = 0;
		for (Medicine medicine : medicines) {
			cnt++;
			logger.info("正在添加第" + cnt + "条。");
			Document document = new Document();
			if (medicine.getName() == null) {
				logger.info(medicine.toString());
			}
			document.add(new TextField("name", medicine.getName(), Store.YES));
			document.add(new TextField("id", medicine.getId(), Store.YES));
			if (medicine.getFunction() != null)
				document.add(new TextField("function", medicine.getFunction(), Store.NO));
			if (medicine.getConponent() != null)
				document.add(new TextField("conponent", medicine.getConponent(), Store.NO));
			document.add(new TextField("flag", "medicine", Store.YES));
			indexWriter.addDocument(document);
		}
		logger.info("添加成功");
	}

	/**
	 * 分页加入symptom索引
	 * @throws IOException
	 */
	private void addSymptom() throws IOException{
		Pageable pageable = new PageRequest(0, 100, Sort.Direction.ASC, "id");
		Page<Symptom> rePage = symptomService.getByPage(pageable);
		while(true){
			addSymptom(rePage.getContent());
			if(!rePage.hasNext()){
				break;
			}
			pageable = pageable.next();
			rePage = symptomService.getByPage(pageable);
		}
	}
	
	/**
	 * 加入症状索引
	 * 
	 * @param symptoms
	 * @throws IOException
	 */
	public void addSymptom(List<Symptom> symptoms) throws IOException {
		logger.info("添加" + symptoms.size() + "条");
		int cnt = 0;
		for (Symptom symptom : symptoms) {
			cnt++;
			logger.info("正在添加第" + cnt + "条。id:" + symptom.getId());
			Document document = new Document();
			document.add(new TextField("name", symptom.getName(), Store.YES));
			document.add(new TextField("id", symptom.getId(), Store.YES));
			if (symptom.getAbstractContent() != null)
				document.add(new TextField("abstract_content", symptom.getAbstractContent(), Store.NO));
			if (symptom.getCause() != null)
				document.add(new TextField("cause", symptom.getCause(), Store.NO));
			if (symptom.getPrevent() != null)
				document.add(new TextField("pervent", symptom.getPrevent(), Store.NO));
			document.add(new TextField("flag", "symptom", Store.YES));
//			logger.info(document.toString());
			indexWriter.addDocument(document);
		}
		logger.info("添加成功");
	}

	/**
	 * neo4j加入索引
	 * 
	 * @param label
	 * @throws IOException
	 */
	public void addToIndexByLable(String label) throws IOException {
		switch (label) {
		case "disease":
			addDisease();
			break;
		case "medicine":
			addMedicine();
			break;
		case "symptom":
			addSymptom();
			break;
		default:
			break;
		}
		indexWriter.close();
	}

	/**
	 * @param url
	 *            pubmed 加入索引
	 */
	public void addToIndexByUrl(String url) {
		try {
			indexWriter = new IndexWriter(directory, indexWriterConfig);
			File file = new File(url);
			String[] files = file
					.list(new AndFileFilter(new PrefixFileFilter("download"), new SuffixFileFilter(".txt")));
			for (String string : files) {
				String temContent = FileUtils.readFileToString(new File(url + "//" + string));
				Document document = new Document();
				document.add(new TextField("content", temContent, Store.YES));
				document.add(new TextField("path", url + "//" + string, Store.YES));
				indexWriter.addDocument(document);
			}
			indexWriter.commit();
			System.out.println("写入成功!共写入" + files.length + "条记录！");
		} catch (IOException e) {
			e.printStackTrace();// @TODO change to logger
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static Analyzer getAnalyzer() {
		return analyzer;
	}

	public static void setAnalyzer(Analyzer analyzer) {
		CreateIndex.analyzer = analyzer;
	}

	public static IndexWriterConfig getIndexWriterConfig() {
		return indexWriterConfig;
	}

	public static void setIndexWriterConfig(IndexWriterConfig indexWriterConfig) {
		CreateIndex.indexWriterConfig = indexWriterConfig;
	}

	public static Directory getDirectory() {
		return directory;
	}

	public static void setDirectory(Directory directory) {
		CreateIndex.directory = directory;
	}

	public static IndexWriter getIndexWriter() {
		return indexWriter;
	}

	public static void setIndexWriter(IndexWriter indexWriter) {
		CreateIndex.indexWriter = indexWriter;
	}

	public static void main(String[] args) throws IOException {
		CreateIndex createIndex = new CreateIndex();
		// createIndex.addToIndexByUrl("C:\\Users\\书生\\Desktop\\毕设\\pubmed\\data");
		createIndex.addToIndexByLable("disease");
	}

}
