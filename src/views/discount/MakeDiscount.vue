<template>
	<Layout>
		<Title title="促销策略"></Title>
		<el-button type="primary" size="medium" @click="chooseModuels1 = true">用户级别</el-button>
		<el-button type="primary" size="medium" @click="chooseModuels2 = true">商品组合</el-button>
		<el-button type="primary" size="medium" @click="chooseModuels3 = true">消费总额</el-button>
		
		<el-dialog
			title="用户级别"
			:visible.sync="chooseModuels1"
			width="40%"
			:before-close="handleClose">
			<div style="width: 90%; margin: 0 auto">
				<el-form :model="discountForm" label-width="100px" ref="discountForm">
					<el-form-item label="级别: " prop="customerLevel">
						<el-select v-model="discountForm.customerLevel" placeholder="请选择客户级别">
							<el-option
								v-for="item in levels"
								:key="item"
								:label="item"
								:value="item">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="折扣: " prop="discount">
						<el-input v-model="discountForm.discount"></el-input>
					</el-form-item>
					<el-form-item label="代金券额度: " prop="voucherAmount">
						<el-input v-model="discountForm.vouvherAmount"></el-input>
					</el-form-item>
				</el-form>
			</div>
			<span slot="footer" class="dialog-footer">
				<el-button type="primary" @click="strategy1()">立即创建</el-button>
			</span>
		</el-dialog>
		<el-dialog
			title="商品组合"
			:visible.sync="chooseModuels2"
			width="40%"
			:before-close="handleClose">
			<div style="width: 90%; margin: 0 auto">
				<el-form :model="discountForm" label-width="100px" ref="discountForm">
					<el-form-item
						v-for="(item, index) in discountForm.bag"
						:key="index"
						:label="'商品' + index">
						<div>
							<el-select v-model="item.pid" placeholder="请选择商品" style="width: 40%;margin-right: 5%">
								<el-option
									v-for="item1 in commdityList"
									:key="item1.id"
									:label="item1.id"
									:value="item1.id">
								</el-option>
							</el-select>
						</div>
						<div style="margin-top: 10px">
							<el-button type="text" size="small" @click="addProduct" v-if="index === discountForm.bag.length - 1">添加</el-button>
							<el-button type="text" size="small" @click.prevent="removeProduct(item)" v-if="index !== 0">删除</el-button>
						</div>
					</el-form-item>
					<el-form-item label="折扣: " prop="discount">
						<el-input v-model="discountForm.discount"></el-input>
					</el-form-item>
					<el-form-item label="代金券额度: " prop="voucherAmount">
						<el-input v-model="discountForm.voucherAmount"></el-input>
					</el-form-item>
				</el-form>
			</div>
			<span slot="footer" class="dialog-footer">
				<el-button type="primary" @click="strategy2">立即创建</el-button>
			</span>
		</el-dialog>
		<el-dialog
			title="消费总额"
			:visible.sync="chooseModuels3"
			width="40%"
			:before-close="handleClose">
			<div style="width: 90%;margin: 0 auto">
				<el-form :model="discountForm" label-width="100px" ref="discountForm">
					<el-form-item label="总额: " prop="totalAmount">
						<el-input v-model="discountForm.totalAmount" placeholder="请输入总额"></el-input>
					</el-form-item>
					<el-form-item label="折扣: " prop="discount">
						<el-input v-model="discountForm.discount"></el-input>
					</el-form-item>
					<el-form-item label="代金券额度: " prop="voucherAmount">
						<el-input v-model="discountForm.voucherAmount"></el-input>
					</el-form-item>
				</el-form>
			</div>
			<span slot="footer" class="dialog-footer">
				<el-button type="primary" @click="strategy3()">立即创建</el-button>
			</span>
		</el-dialog>
		<br>
		<br>
		<el-button type="primary" size="medium" @click="showD">{{flag}}</el-button>
		<div v-if="flag === '结束查看'" class="mt15">
			<div class="mt15">
				<el-table
					:data="discountList"
					stripe
					style="width: 100%"
					:header-cell-style="{'text-align':'center'}"
					:cell-style="{'text-align':'center'}"
					class="mt15">
					<el-table-column
						prop="id"
						label="id"
						width="150">
					</el-table-column>
					<el-table-column
						prop="type"
						label="折扣类型"
						width="150">
					</el-table-column>
					<el-table-column
						prop="customerLevel"
						label="客户级别"
						width="150">
					</el-table-column>
					<el-table-column
						label="商品组合包"
						width="200">
						<template slot-scope="scope">
							<el-button type="text" @click="showBag(scope.row)">查看商品组合包</el-button>
						</template>
					</el-table-column>
					<el-table-column
						prop="totalAmount"
						label="总额"
						width="200">
					</el-table-column>
					<el-table-column
						prop="discount"
						label="折扣"
						width="200">
					</el-table-column>
					<el-table-column
						prop="voucherAmount"
						label="代金券"
						width="200">
					</el-table-column>
					<el-table-column
						label="时间">
						<template slot-scope="scope">
							{{formatDate(scope.row.createTime)}}
						</template>
					</el-table-column>
					<el-table-column
						label="操作">
						<template slot-scope="scope">
							<el-button
								@click.native.prevent="deleteDiscount(scope.row.id)"
								type="text"
								size="small">
								删除
							</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>
		</div>
		
		<el-dialog
			title="商品组合包"
			:visible.sync="dialogVisible"
			width="80%"
			:before-close="handleClose">
			<div>商品id</div>
			<div>{{curBag}}</div>
		</el-dialog>
	</Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import {makeDiscount, deleteDiscount, showDiscount} from "@/network/discount";
import {getAllCommodity} from "@/network/commodity";
import {formatDate} from "@/common/utils";

export default {
	components: {
		Layout,
		Title
	},
	data() {
		return {
			dialogVisible:false,
			flag:'查看折扣',
			chooseModuels1:false,
			chooseModuels2:false,
			chooseModuels3:false,
			levels:[1,2,3,4,5,6],
			discountForm: {
				type:'',
				customerLevel:'',
				bag:[{
					pid:'',
				}],
				totalAmount:'',
				voucherAmount:'',
				discount:'',
			},
			cur_row:{},
			commodityList:[],
			discountList:[],
		}
	},
	mounted() {
		getAllCommodity({}).then(_res => {
			let res = _res.result
			res.forEach(item => this.commodityList.push({ id:item.id }))
			console.log(this.commodityList)
		})
		showDiscount({}).then(_res => {
			let list = _res.result
			for (let i in list) {
				let tempList = {}
				tempList.id = list[i].id - 32
				tempList.type = list[i].type
				tempList.customerLevel = list[i].customerLevel
				tempList.totalAmount = list[i].totalAmount
				tempList.discount = list[i].discount
				tempList.voucherAmount = list[i].voucherAmount
				tempList.bag = list[i].bag
				tempList.createTime = list[i].createTime
				this.discountList.push(tempList)
			}
		})
	},
	computed: {
		curBag() {
			let ans = ''
			for (let i in this.cur_row.bag) {
				ans = ans + this.cur_row.bag[i].pid
				if(i != this.cur_row.bag.length - 1) {
					ans = ans + '==='
				}
			}
			return ans
		}
	},
	methods: {
		handleClose(done) {
			this.$confirm('确认关闭？').then(_ => {
				this.resetForm()
				done()
			}).catch(_ => {})
		},
		resetForm() {
			this.discountForm = {
				type:'',
				customerLevel:'',
				bag:[{
					pid:''
				}],
				totalAmount:'',
				voucherAmount:'',
				discount:'',
			}
		},
		strategy1() {
			this.discountForm.type = 1
			this.discountForm.customerLevel = Number(this.discountForm.customerLevel)
			this.discountForm.discount = Number(this.discountForm.discount)
			this.discountForm.voucherAmount = Number(this.discountForm.voucherAmount)
			makeDiscount(this.discountForm).then(_res => {
				if (_res.msg === 'Success') {
					this.$message.success('创建成功!')
					this.chooseModuels1 = false
					this.resetForm()
				}
			})
		},
		strategy2() {
			this.discountForm.type = 2
			this.discountForm.discount = Number(this.discountForm.discount)
			this.discountForm.voucherAmount = Number(this.discountForm.voucherAmount)
			console.log(this.discountForm.bag)
			makeDiscount(this.discountForm).then(_res => {
				if (_res.msg === 'Success') {
					this.$message.success('创建成功!')
					this.chooseModuels2 = false
					this.resetForm()
				}
			})
		},
		strategy3() {
			this.discountForm.type = 3
			this.discountForm.totalAmount = Number(this.discountForm.totalAmount)
			this.discountForm.discount = Number(this.discountForm.discount)
			this.discountForm.voucherAmount = Number(this.discountForm.discount)
			makeDiscount(this.discountForm).then(_res => {
				if (_res.msg === 'Success') {
					this.$message.success('创建成功!')
					this.chooseModuels3 = false
					this.resetForm()
				}
			})
		},
		addProduct() {
			this.discountForm.bag.push({})
		},
		removeProduct(item) {
			let index = this.discountForm.bag.indexOf(item)
			if (index !== -1) {
				this.discountForm.bag.splice(index, 1)
			}
		},
		showD() {
			if (this.flag === '查看折扣') {
				this.flag = '结束查看'
			} else {
				this.flag = '查看折扣'
			}
		},
		showBag(row) {
			if( row.type === 2) {
				this.dialogVisible = true
				this.cur_row = row
			}
		},
		deleteDiscount(id) {
			let config = {
				params: {
					id: Number(id + 32)
				}
			};
			this.$confirm('是否取消该促销?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				deleteDiscount(config).then(_res => {
					if (_res.msg === 'Success') {
						this.$message.success('删除成功!')
					}
				})
			}).catch(() => {
				this.$message({
					type: 'info',
					message: '已取消删除'
				})
			})
		},
		formatDate
	}
}
</script>

<style lang='scss' scoped>
.body {
    margin: 0 auto;
    margin-top: 10px;
    height: 80vh;
    overflow-y: auto;
    width: 100%;
    background: rgba($color: #fff, $alpha: 0.5);
}
</style>