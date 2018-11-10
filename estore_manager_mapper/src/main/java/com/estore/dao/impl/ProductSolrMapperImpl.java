package com.estore.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.search.SentDateTerm;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.zookeeper.Op.SetData;
import org.springframework.beans.factory.annotation.Autowired;

import com.estore.bean.Product;
import com.estore.bean.ProductModel;
import com.estore.bean.ProductSearch;
import com.estore.dao.ProductSolrMapper;

public class ProductSolrMapperImpl implements ProductSolrMapper {
	@Autowired
	SolrServer solrServer;

	@Override
	public void deleteAll() throws Exception{
		// TODO Auto-generated method stub
		//删除全部，第一个参数是设置需要删除的数据的域和值，第二个是执行后多久进行删除操作
			solrServer.deleteByQuery("*:*",1000);
		
	}

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception{
		// TODO Auto-generated method stub
		//删除某个特定域的特定值的数据
			solrServer.deleteByQuery("id:" + id,1000);
	}

	@Override
	public int add(Product product) throws Exception{
		// TODO Auto-generated method stub
		
		//创建新的文档对象
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		//设置文档的域
		SetData(solrInputDocument,product);
		//进行添加
		solrServer.add(solrInputDocument);
		//进行手动提交，否则无法进行添加
		solrServer.commit();
		return 0;
	}

	private void SetData(SolrInputDocument solrInputDocument, Product product) {
		// TODO Auto-generated method stub
		//设置文档的域
		solrInputDocument.setField("id", product.getId());
		solrInputDocument.setField("product_name", product.getName());
		solrInputDocument.setField("product_category_id", product.getCategoryId());
		solrInputDocument.setField("product_description", product.getDescription());
		solrInputDocument.setField("product_img_path", product.getImgPath());
		solrInputDocument.setField("product_pnum", product.getPnum());
		solrInputDocument.setField("product_price", product.getPrice());
	}

	@Override
	public int updateByPrimaryKey(Product product) throws Exception {
		// TODO Auto-generated method stub
		//修改其实和添加是一样的，因为只要添加的ID是一样的，那么就会把原来的删除了，然后再添加一个
		add(product);
		return 0;
	}

	@Override
	public List<ProductModel> selectByProductSearch(ProductSearch productSearch) throws Exception {
		//solrServer = new HttpSolrServer("http://localhost/solr/collection1");
		
		SolrQuery solrQuery = new SolrQuery();
		//设置关键字
		solrQuery.setQuery(productSearch.getQueryString());
		//设置默认检索域
		solrQuery.set("df", "product_keywords");
		//设置过滤条件
//		if(null != productSearch.getCatalog_name() && !"".equals(productSearch.getCatalog_name())){
//			solrQuery.set("fq", "product_catalog_name:" + productSearch.getCatalog_name());
//		}
		if(null != productSearch.getPrice() && !"".equals(productSearch.getPrice())){
			//0-9   50-*  对价格进行过滤
			String[] p = productSearch.getPrice().split("-");
			solrQuery.set("fq", "product_price:[" + p[0] + " TO " + p[1] + "]");
		}
		// 价格排序
		if ("1".equals(productSearch.getSort())) {
			solrQuery.addSort("product_price", ORDER.desc);
		} else {
			solrQuery.addSort("product_price", ORDER.asc);
		}
		// 分页
		solrQuery.setStart(0);
		solrQuery.setRows(16);
		// 只查询指定域
		solrQuery.set("fl", "id,product_name,product_price,product_img_path,product_description,product_category_name");
		// 高亮
		// 打开开关
		solrQuery.setHighlight(true);
		// 指定高亮域
		solrQuery.addHighlightField("product_name");
		// 前缀 style=\"color:red\">
		solrQuery.setHighlightSimplePre("<b>");
		solrQuery.setHighlightSimplePost("</b>");
		// 执行查询
		QueryResponse response = solrServer.query(solrQuery);
		// 文档结果集
		SolrDocumentList docs = response.getResults();

		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		List<ProductModel> productModels = new ArrayList<ProductModel>();
		
//		System.out.println("response: "+response);
//		System.out.println("docs: "+docs);
//		System.out.println("highlighting: "+highlighting);
		
		for (SolrDocument doc : docs) {
			
			//封装数据, 将查询结果封装成bean,  下面的set方法容易报 null pointer exception
			ProductModel productModel = new ProductModel();
			productModel.setId((String) doc.get("id"));
			productModel.setPrice((Float) doc.get("product_price"));
			productModel.setDescription((String) doc.get("product_description"));
			productModel.setImg_path((String) doc.get("product_img_path"));
			productModel.setCategory_name((String) doc.get("product_category_name"));
			
			Map<String, List<String>> map = highlighting.get((String) doc.get("id"));
			List<String> list = map.get("product_name");
			
			productModel.setName(list.get(0));
			productModels.add(productModel);
		}
		return productModels;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//测试
	void ss() throws SolrServerException {
		
		//创建查询数据对象（便于设置查询条件）
		SolrQuery solrQuery = new SolrQuery();
		//设置查询的域和值，这个在之后的项目中可以用于动态
		//方法一：参数q就代表query查询
		//solrQuery.set("q","name:佘超伟123");
		//方法二：(一般使用该方法)
		solrQuery.setQuery("name:佘超伟");
		//方法三：通过设置默认域
		//solrQuery.set("df", "name");
		//solrQuery.setQuery("佘超伟");
		
		//设置查询过滤条件(可以设置多个，只要域和值有改变就可以了)
		//solrQuery.set("fq", "id:haha123");
		//添加排序方式（可选内容）
		//solrQuery.addSort("需要排序的域",ORDER.asc);//升序
		//solrQuery.addSort("需要排序的域",ORDER.desc);//降序
		//设置分页处理(比如这是设置每次显示5个)
		solrQuery.setStart(0);
		solrQuery.setRows(5);
		//设置只查询显示指定的域和值(第二个参数可以是多个，之间用“逗号”分割)
		//solrQuery.set("fl", "name");
		//设置某域进行高亮显示
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("name");
		//设置高亮显示格式的前后缀
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		solrQuery.setHighlightSimplePost("</span");	
		
		//执行查询，获得查询结果对象
		QueryResponse query = solrServer.query(solrQuery);
		//获取查询的结果集
		SolrDocumentList results = query.getResults();
		//获取高亮显示的查询结果
		//注意点：因为高亮的结果和正常的查询结果是不一样的，所以要进行特别的处理
		Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
		//遍历结果集
		for (SolrDocument solrDocument : results) {
			String idStr = (String) solrDocument.get("id");
			System.out.println("id----------------" + idStr);
			String nameStr = (String) solrDocument.get("name");
			System.out.println("name----------------" + nameStr);
			System.out.println("===========高亮显示=====================");
			Map<String, List<String>> map = highlighting.get(idStr);
			List<String> list = map.get("name");
			String resultString = list.get(0);
			System.out.println("高亮结果为：-----" + resultString);
		}		
		//return null;
	}

}
