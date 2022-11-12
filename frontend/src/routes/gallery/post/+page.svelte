<script>
	import axios from 'axios';
	import { onMount } from 'svelte';
	import { goto } from '$app/navigation';

	let gallery = {};
	let photos = [];
	/**
	 * @type {HTMLInputElement}
	 */

	onMount(async () => {});
	function upload() {
		goto('/gallery/post/1');
	}
</script>

<svelte:head>
	<title>About</title>
	<meta name="description" content="About this app" />
</svelte:head>

<div class="text-column">
	<br />
	Title
	<input type="text" name="title" />
	<br />
	Description
	<input type="text" name="description" />

	<button on:click|once={upload}> Click me </button>
</div>
