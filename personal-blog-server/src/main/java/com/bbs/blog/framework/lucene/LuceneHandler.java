package com.bbs.blog.framework.lucene;

import com.bbs.entity.Blog;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * 基于lucene的搜索
 * Created by m1584 on 2018/7/8.
 */
public class LuceneHandler {

    private final static Logger LOG = LoggerFactory.getLogger(LuceneHandler.class);
    private Directory directory;
    private IndexWriter indexWriter;
    private IndexReader indexReader;
    private SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
    private final static String BASE_URL = "";

    private IndexWriter getWriteIndex() throws IOException {
        directory = FSDirectory.open(Paths.get(BASE_URL));
        indexWriter = new IndexWriter(directory, new IndexWriterConfig(analyzer));
        return indexWriter;
    }

    // 创建索引
    public void writeToIndex(List<Blog> blogList) throws IOException {
        try {
            IndexWriter indexWriter = getWriteIndex();
            List<Document> docs = new ArrayList<>(blogList.size());
            Document doc = null;
            for (Blog blog : blogList) {
                doc = new Document();
                doc.add(new TextField("blogId", blog.getId(), Field.Store.YES));
                doc.add(new TextField("blogTitle", blog.getTitle(), Field.Store.YES));
                doc.add(new TextField("blogContent", blog.getContent(), Field.Store.YES));
                docs.add(doc);
            }
            indexWriter.addDocuments(docs);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.toString());
        } finally {
            closeWriteIndex();
        }
    }

    // 更新索引
    public void updateIoIndex() {
    }

    // 移除索引
    public void removeToIndex() {
    }

    // 读取索引
    public void readToIndex() {


    }

    private void closeWriteIndex() throws IOException {
        if (indexWriter != null) {
            indexWriter.close();
        }
    }

    private void closeReadIndex() throws IOException {
        if (indexReader != null) {
            indexReader.close();
        }
    }
}
