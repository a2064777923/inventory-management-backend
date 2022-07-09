<template>
  <Layout>
    <Title title="库存盘点"></Title>
    （盘点的是当天的库存快照，包括当天的各种商品的名称，型号，库存数量，库存均价，批次，批号，出厂日期，并且显示行号。要求可以导出Excel。
    <el-button @click="exportAsExcel">导出Excel</el-button>
    <div class="table-body">
      <el-table
        :data="cur_list"
        style="width: 100%;"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
      >
        <el-table-column fixed type="index" label="行号" min-width="12%">
        </el-table-column>
        <el-table-column label="商品Id" min-width="13%">
          <template slot-scope="scope">
            <el-button type="text" @click="showProduct(scope.row)">{{ scope.row.product.id }}</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="quantity" label="数量" min-width="12%">
        </el-table-column>
        <el-table-column prop="batchId" label="批次" min-width="12%">
        </el-table-column>
        <el-table-column prop="purchasePrice" label="价格" min-width="12%">
        </el-table-column>
        <el-table-column prop="productionDate" label="日期" min-width="12%">
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
      title="商品详细信息"
      :visible.sync="dialogVisible"
      width="80%"
      :before-close="handleClose">
      <div>
        <el-table
          :data="cur_product"
          style="width: 100%;"
          :header-cell-style="{'text-align': 'center'}"
          :cell-style="{'text-align': 'center'}">
          <el-table-column
            fixed
            prop="id"
            label="编号"
            min-width="15%">
          </el-table-column>
          <el-table-column
            prop="name"
            label="名称"
            min-width="10%">
          </el-table-column>
          <el-table-column
            prop="type"
            label="型号"
            min-width="15%">
          </el-table-column>
          <el-table-column
            prop="quantity"
            label="库存数量"
            min-width="8%">
          </el-table-column>
          <el-table-column
            prop="purchasePrice"
            label="进价"
            min-width="8%">
          </el-table-column>
          <el-table-column
            prop="retailPrice"
            label="零售价"
            min-width="8%">
          </el-table-column>
          <el-table-column
            prop="recentPp"
            label="最近进价"
            min-width="8%">
          </el-table-column>
          <el-table-column
            prop="recentRp"
            label="最近零售价"
            min-width="8%">
          </el-table-column>
        </el-table>
      </div>

    </el-dialog>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import ExportJsonExcel from "js-export-excel"
import { getWarehouseCounting,warehouseExportToExcel } from "@/network/warehouse";

export default {
  components: {
    Layout,
    Title
  },
  data(){
    return {
      cur_list: [],
      cur_row: {},
      export_list: [],
      dialogVisible: false,
    }
  },
  computed: {
    cur_product() {
      let temp = [];
      temp.push(this.cur_row.product);
      return temp;
    }
  },
  mounted() {
    getWarehouseCounting().then(_res => {
      console.log("库存盘点", _res);
      this.cur_list = _res.result;
      this.cur_row = this.cur_list[0];
    })

  },
  methods: {
    //author:HongZiXian
    //date:2022/05/30
   async exportAsExcel() {
      await warehouseExportToExcel().then(_res =>{
        this.export_list = _res.result;
        console.log("导出狀態:",_res.msg);
      })
      let excelFile = {}; //excel文件
      let sheet = [];//數據表
      console.log("el:",this.export_list);
      if(this.export_list.length > 0){
        for(let i in this.export_list){
          let product = this.export_list[i].product;
          let data = {
            名称:product.name,
            型号:product.type,
            库存数量:this.export_list[i].quantity,
            库存均价:this.export_list[i].purchasePrice,
            批次:this.export_list[i].batchId,
            批号:product.categoryId,
            出厂日期:this.export_list[i].productionDate,
          };
          sheet.push(data);
        }
        excelFile.fileName = "庫存盤点記錄XLS";
        excelFile.datas = [{
          //excel文件的数据源
            sheetData: sheet,
          //excel文件sheet的表名
            sheetName: "sheet1",
          //excel文件表头名
            sheetHeader: ["名称","型号","库存数量","库存均价","批次","批号","出厂日期"],
          //excel文件列名
            sheetFilter: ["名称","型号","库存数量","库存均价","批次","批号","出厂日期"],
          },
        ];
        //创建ExportJsonExcel实例对象
        let toExcel = new ExportJsonExcel(excelFile);
        //调用保存方法
        toExcel.saveExcel();
      }else{
        this.$message.error("無庫存盤点記錄!");
      }
      //TODO 导出Excel
    },
    showProduct(row) {
      this.cur_row = row;
      this.dialogVisible = true;
    },
    handleClose(done) {
      done();
    }
  }
};
</script>

<style scoped>

.table-body {
  width: 80%;
  margin: 20px auto;
}
</style>
